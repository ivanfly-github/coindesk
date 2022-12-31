package com.cathay.coindesk.ex;

public class BpiException extends RuntimeException  {

	private static final long serialVersionUID = 4719088513466306294L;

	public BpiException(String message, Throwable cause) {
		super(message, cause);
	}

	public BpiException(String message) {
		super(message);
	}

	public BpiException(Throwable cause) {
		super(cause);
	}

}
