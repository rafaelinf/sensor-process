package br.com.sensor.process.exception;

public class TemperatureException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TemperatureException() {
		super();
	}

	public TemperatureException(String s) {
		super(s);
	}

	public TemperatureException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public TemperatureException(Throwable throwable) {
		super(throwable);
	}
}