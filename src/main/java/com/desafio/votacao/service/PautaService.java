package com.desafio.votacao.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.votacao.dto.PautaRequest;
import com.desafio.votacao.exception.ResourceNotFoundException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.repository.PautaRepository;

import lombok.RequiredArgsConstructor;

@Service("pautaService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class PautaService {

	private final PautaRepository pautaRepository;

	@Transactional
	public Pauta criarPauta(PautaRequest pautaRequest) {
		Pauta pauta = Pauta.builder() //
				.descricao(pautaRequest.descricao()) //
				.dataCriacao(OffsetDateTime.now()) //
				.build();

		return pautaRepository.persist(pauta);
	}

	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}

	public Pauta findById(Long id) {
		return pautaRepository.findById(id) //
				.orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));
	}

}
