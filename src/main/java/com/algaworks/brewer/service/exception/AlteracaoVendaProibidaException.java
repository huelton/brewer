package com.algaworks.brewer.service.exception;

public class AlteracaoVendaProibidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AlteracaoVendaProibidaException (String message) {
		super(message);
	}
}
