package com.desafio.votacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.votacao.client.CpfVoteEligibilityClient;
import com.desafio.votacao.client.CpfVoteEligibilityResult;
import com.desafio.votacao.dto.VotacaoRequest;
import com.desafio.votacao.dto.VotoRequest;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Associado;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Votacao;
import com.desafio.votacao.repository.VotacaoRepository;
import com.desafio.votacao.repository.VotoRepository;

@ExtendWith(MockitoExtension.class)
class VotacaoServiceTest {

	@Mock
	private VotacaoRepository votacaoRepository;
	@Mock
	private VotoRepository votoRepository;
	@Mock
	private PautaService pautaService;
	@Mock
	private AssociadoService associadoService;
	@Mock
	private CpfVoteEligibilityClient cpfVoteEligibilityClient;

	@InjectMocks
	private VotacaoService votacaoService;

	private Pauta pauta;
	private Associado associado;

	@BeforeEach
	void setUp() {
		pauta = Pauta.builder().id(1L).descricao("Nova pauta").dataCriacao(OffsetDateTime.now()).build();
		associado = Associado.builder().id(1L).cpf("52998224725").dataCriacao(OffsetDateTime.now()).build();
	}

	@Test
	void shouldOpenVotingSession() {
		when(votacaoRepository.existsByPautaId(1L)).thenReturn(false);
		when(pautaService.findById(1L)).thenReturn(pauta);
		when(votacaoRepository.persist(any())).thenAnswer(invocation -> {
			Votacao votacao = invocation.getArgument(0);
			return Votacao.builder().id(10L).pauta(votacao.getPauta()).dataAbertura(votacao.getDataAbertura())
					.dataEncerramento(votacao.getDataEncerramento()).build();
		});

		var response = votacaoService.abrirVotacao(1L, new VotacaoRequest(5));

		assertEquals(10L, response.getId());
		assertEquals(1L, response.getPauta().getId());
	}

	@Test
	void shouldNotAllowDuplicateVote() {
		Votacao votacao = Votacao.builder().id(3L).pauta(pauta).dataAbertura(OffsetDateTime.now().minusMinutes(1))
				.dataEncerramento(OffsetDateTime.now().plusMinutes(1)).build();

		when(votacaoRepository.findByPautaId(1L)).thenReturn(Optional.of(votacao));
		when(associadoService.findById(1L)).thenReturn(associado);
		when(cpfVoteEligibilityClient.check("52998224725")).thenReturn(new CpfVoteEligibilityResult("ABLE_TO_VOTE"));
		when(votoRepository.existsByVotacaoIdAndAssociadoId(3L, 1L)).thenReturn(true);

		assertThrows(BusinessException.class, () -> votacaoService.votar(1L, new VotoRequest(1L, "SIM")));
		verify(votoRepository, never()).persist(any());
	}

	@Test
	void shouldRejectUnableToVoteStatus() {
		Votacao votacao = Votacao.builder().id(3L).pauta(pauta).dataAbertura(OffsetDateTime.now().minusMinutes(1))
				.dataEncerramento(OffsetDateTime.now().plusMinutes(1)).build();

		when(votacaoRepository.findByPautaId(1L)).thenReturn(Optional.of(votacao));
		when(associadoService.findById(1L)).thenReturn(associado);
		when(cpfVoteEligibilityClient.check("52998224725")).thenReturn(new CpfVoteEligibilityResult("UNABLE_TO_VOTE"));

		assertThrows(BusinessException.class, () -> votacaoService.votar(1L, new VotoRequest(1L, "SIM")));
	}
}
