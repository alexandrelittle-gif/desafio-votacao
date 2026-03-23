package com.desafio.votacao.service;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.votacao.client.CpfVoteEligibilityClient;
import com.desafio.votacao.dto.ApuracaoResponse;
import com.desafio.votacao.dto.VotacaoRequest;
import com.desafio.votacao.dto.VotoRequest;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.exception.ResourceNotFoundException;
import com.desafio.votacao.model.Associado;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Votacao;
import com.desafio.votacao.model.VoteValue;
import com.desafio.votacao.model.Voto;
import com.desafio.votacao.repository.VotacaoRepository;
import com.desafio.votacao.repository.VotoRepository;

import lombok.RequiredArgsConstructor;

@Service("votacaoService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class VotacaoService {

	private final VotacaoRepository votacaoRepository;

	private final PautaService pautaService;

	private final AssociadoService associadoService;

	private final VotoRepository votoRepository;

	private final CpfVoteEligibilityClient cpfVoteEligibilityClient;

	@Transactional
	public Votacao abrirVotacao(Long pautaId, VotacaoRequest votacaoRequest) throws BusinessException {
		Pauta pauta = pautaService.findById(pautaId);
		if (votacaoRepository.existsByPautaId(pautaId)) {
			throw new BusinessException("Já existe uma votação para essa pauta!");
		}
		int minutosVotacao = Objects.nonNull(votacaoRequest.duracaoMinutos()) ? votacaoRequest.duracaoMinutos() : 1;
		OffsetDateTime dataAbertura = OffsetDateTime.now();
		OffsetDateTime dataEncerramento = dataAbertura.plusMinutes(minutosVotacao);
		Votacao votacao = Votacao.builder() //
				.dataAbertura(dataAbertura) //
				.dataEncerramento(dataEncerramento) //
				.pauta(pauta) //
				.build();
		return votacaoRepository.persist(votacao);
	}

	@Transactional
	public Voto votar(Long pautaId, VotoRequest votoRequest) throws BusinessException {
		Votacao votacao = votacaoRepository.findByPautaId(pautaId).orElseThrow(
				() -> new ResourceNotFoundException("Sessão de votação não encontrada para a pauta informada"));

		OffsetDateTime now = OffsetDateTime.now();
		if (!votacao.isAberta(now)) {
			throw new BusinessException("Sessão de votação encerrada");
		}
		Associado associado = associadoService.findById(votoRequest.idAssociado());

		String status = cpfVoteEligibilityClient.check(associado.getCpf()).status();
		if (!"ABLE_TO_VOTE".equals(status)) {
			throw new BusinessException("Associado não está habilitado a votar no momento");
		}

		if (votoRepository.existsByVotacaoIdAndAssociadoId(votacao.getId(), associado.getId())) {
			throw new BusinessException("Associado já votou nessa pauta!");
		}

		Voto voto = Voto.builder().votacao(votacao).associado(associado).resposta(VoteValue.from(votoRequest.voto()))
				.dataCriacao(now).build();

		return votoRepository.persist(voto);
	}

	public ApuracaoResponse apuracao(Long pautaId) {
		Votacao votacao = votacaoRepository.findByPautaId(pautaId).orElseThrow(
				() -> new ResourceNotFoundException("Sessão de votação não encontrada para a pauta informada"));

		return votacaoRepository.apuracao(votacao.getId());
	}
}
