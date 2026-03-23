package com.desafio.votacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.desafio.votacao.dto.ApuracaoResponse;
import com.desafio.votacao.model.Associado;
import com.desafio.votacao.model.Votacao;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface VotacaoRepository extends BaseJpaRepository<Votacao, Long> {
	
	@Query(value = "select \r\n"
			+ "	p.id idPauta,\r\n"
			+ "	p.descricao pauta,\r\n"
			+ "	case when now() between v.data_abertura and v.data_encerramento then 'Aberta' else 'Encerrada' end status,\r\n"
			+ "	count(v1) filter (where v1.resposta = 'SIM') votosSim,\r\n"
			+ "	count(v1) filter (where v1.resposta = 'NAO') votosNao,\r\n"
			+ "	count(v1) total,\r\n"
			+ "	case\r\n"
			+ "		when count(v1) filter (where v1.resposta = 'SIM') = count(v1) filter (where v1.resposta = 'NAO') then 'Empate'\r\n"
			+ "		when count(v1) filter (where v1.resposta = 'SIM') > count(v1) filter (where v1.resposta = 'NAO') then 'Sim'\r\n"
			+ "		else 'Não'\r\n"
			+ "	end resultado\r\n"
			+ "from \r\n"
			+ "	votacoes v\r\n"
			+ "join pautas p on p.id = v.pauta_id\r\n"
			+ "left join votos v1 on v1.votacao_id = v.id\r\n"
			+ "where \r\n"
			+ "	v.id = :idVotacao\r\n"
			+ "group by \r\n"
			+ "	p.id, p.descricao, v.data_abertura, v.data_encerramento", nativeQuery = true)
	public ApuracaoResponse apuracao(Long idVotacao);

	public boolean existsByPautaId(Long pautaId);

	public Optional<Votacao> findByPautaId(Long pautaId);

}
