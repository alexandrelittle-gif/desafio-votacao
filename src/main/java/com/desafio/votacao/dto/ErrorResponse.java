package com.desafio.votacao.dto;

import java.util.List;

public record ErrorResponse(int status, String error, String message, List<String> details) {

}
