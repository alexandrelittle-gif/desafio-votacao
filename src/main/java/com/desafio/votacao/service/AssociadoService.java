package com.desafio.votacao.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.votacao.dto.AssociadoRequest;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.exception.ResourceNotFoundException;
import com.desafio.votacao.model.Associado;
import com.desafio.votacao.repository.AssociadoRepository;

import lombok.RequiredArgsConstructor;

@Service("associadoService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class AssociadoService {

	private final AssociadoRepository associadoRepository;

	@Transactional
	public Associado criarAssociado(AssociadoRequest associadoRequest) throws BusinessException {
		associadoRepository.findByCpf(associadoRequest.cpf()).ifPresent(_ -> {
			throw new BusinessException("Já existe um associado com esse cpf");
		});

		Associado associado = Associado.builder() //
				.cpf(associadoRequest.cpf()) //
				.dataCriacao(OffsetDateTime.now()) //
				.build();

		return associadoRepository.persist(associado);
	}

	public List<Associado> findAll() {
		return associadoRepository.findAll();
	}

	public Associado findById(Long id) {
		return associadoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Associado não encontrado"));
	}

}
