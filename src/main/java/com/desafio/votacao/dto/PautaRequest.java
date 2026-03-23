package com.desafio.votacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PautaRequest(@NotBlank(message = "descrição é obrigatória") //
@Size(max = 255, message = "descricao deve ter no máximo 255 caracteres") //
String descricao) {

}
