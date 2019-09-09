package br.com.sensor.process.exception;

public class AlertException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlertException() {
		super();
	}

	public AlertException(String s) {
		super(s);
	}

	public AlertException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AlertException(Throwable throwable) {
		super(throwable);
	}
}
