package com.cathay.coindesk.ex;

public class BpiIllegalArgumentException extends BpiException {


	private static final long serialVersionUID = -789942317406122389L;

	public BpiIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public BpiIllegalArgumentException(String message) {
		super(message);
	}

	public BpiIllegalArgumentException(Throwable cause) {
		super(cause);
	}

}
