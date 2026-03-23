package com.desafio.votacao.dto;

public record ApuracaoResponse(Long idPauta, String pauta, String status, Long votosSim, Long votosNao, Long total, String resultado) {

}
