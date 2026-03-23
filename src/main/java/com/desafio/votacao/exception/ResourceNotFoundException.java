package com.desafio.votacao.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3166414739821766232L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
