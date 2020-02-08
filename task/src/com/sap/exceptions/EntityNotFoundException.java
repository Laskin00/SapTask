package com.sap.exceptions;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String arg0) {
		super(arg0);
	}

	public EntityNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public EntityNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}


}
