package com.desafio.votacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AssociadoRequest(
		@NotBlank(message = "cpf é obrigatório") //
		@Pattern(regexp = "\\d{11}", message = "cpf deve conter 11 dígitos numéricos") //
		String cpf) {

}
