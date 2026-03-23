package com.desafio.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.votacao.dto.AssociadoRequest;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Associado;
import com.desafio.votacao.service.AssociadoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v{version}/associados")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Associados")
public class AssociadoController {

	private final AssociadoService associadoService;

	@PostMapping(version = "1.0")
	@ResponseStatus(HttpStatus.CREATED)
	public Associado cadastrarAssociado(@Valid @RequestBody AssociadoRequest associadoRequest)
			throws BusinessException {
		Associado associado = associadoService.criarAssociado(associadoRequest);
		return associado;
	}

	@GetMapping(version = "1.0")
	public List<Associado> findAllAssociados() {
		List<Associado> associados = associadoService.findAll();
		return associados;
	}

}
