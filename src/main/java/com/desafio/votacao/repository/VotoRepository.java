package com.desafio.votacao.repository;

import com.desafio.votacao.model.Voto;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface VotoRepository extends BaseJpaRepository<Voto, Long> {

	boolean existsByVotacaoIdAndAssociadoId(Long votacaoId, Long associadoId);

}
