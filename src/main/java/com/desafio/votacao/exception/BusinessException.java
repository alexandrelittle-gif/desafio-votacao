package com.desafio.votacao.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6893599764199828344L;

	public BusinessException(String message) {
		super(message);
	}

}
