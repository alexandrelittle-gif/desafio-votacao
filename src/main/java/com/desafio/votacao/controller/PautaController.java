package com.desafio.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.votacao.dto.ApuracaoResponse;
import com.desafio.votacao.dto.PautaRequest;
import com.desafio.votacao.dto.VotacaoRequest;
import com.desafio.votacao.dto.VotoRequest;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Votacao;
import com.desafio.votacao.model.Voto;
import com.desafio.votacao.service.PautaService;
import com.desafio.votacao.service.VotacaoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v{version}/pautas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Pautas")
public class PautaController {

	private final PautaService pautaService;

	private final VotacaoService votacaoService;

	@PostMapping(version = "1.0")
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta cadastrarPauta(@Valid @RequestBody PautaRequest pautaRequest) {
		Pauta pauta = pautaService.criarPauta(pautaRequest);
		return pauta;
	}

	@GetMapping(version = "1.0")
	public List<Pauta> findAllPautas() {
		List<Pauta> pautas = pautaService.findAll();
		return pautas;
	}

	@PostMapping(value = "/{pautaId}/votacao", version = "1.0")
	@ResponseStatus(HttpStatus.CREATED)
	public Votacao abrirVotacao(@PathVariable Long pautaId, @Valid @RequestBody VotacaoRequest votacaoRequest)
			throws BusinessException {
		Votacao votacao = votacaoService.abrirVotacao(pautaId, votacaoRequest);
		return votacao;
	}

	@PostMapping(value = "/{pautaId}/voto", version = "1.0")
	@ResponseStatus(HttpStatus.CREATED)
	public Voto votar(@PathVariable Long pautaId, @Valid @RequestBody VotoRequest votoRequest) throws BusinessException {
		Voto voto = votacaoService.votar(pautaId, votoRequest);
		return voto;
	}

	@GetMapping(value = "/{pautaId}/apuracao", version = "1.0")
	public ApuracaoResponse apuracao(@PathVariable Long pautaId) throws BusinessException {
		ApuracaoResponse apuracaoResponse = votacaoService.apuracao(pautaId);
		return apuracaoResponse;
	}

}
