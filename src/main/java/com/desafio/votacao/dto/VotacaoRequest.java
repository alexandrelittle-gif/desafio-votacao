package com.desafio.votacao.dto;

import jakarta.validation.constraints.Min;

public record VotacaoRequest(
		@Min(value = 1, message = "duracaoMinutos deve ser maior que zero") Integer duracaoMinutos) {

}
