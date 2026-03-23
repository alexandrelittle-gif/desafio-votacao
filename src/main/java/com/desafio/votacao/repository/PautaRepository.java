package com.desafio.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.desafio.votacao.model.Pauta;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface PautaRepository extends BaseJpaRepository<Pauta, Long> {
	
	@Query("from Pauta")
	public List<Pauta> findAll();

}
