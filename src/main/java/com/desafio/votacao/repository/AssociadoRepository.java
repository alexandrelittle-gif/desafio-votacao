package com.desafio.votacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.desafio.votacao.model.Associado;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface AssociadoRepository extends BaseJpaRepository<Associado, Long> {

	@Query("from Associado")
	public List<Associado> findAll();

	Optional<Associado> findByCpf(String cpf);
}
