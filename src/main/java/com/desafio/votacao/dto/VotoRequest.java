package com.desafio.votacao.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VotoRequest(
		@NotNull(message = "id do associado é obrigatório") @Min(value = 1, message = "associadoId deve ser maior que zero") Long idAssociado,
		@NotBlank(message = "voto é obrigatório") String voto) {

}
