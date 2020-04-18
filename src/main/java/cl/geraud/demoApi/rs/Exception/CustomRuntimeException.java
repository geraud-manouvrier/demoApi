package cl.geraud.demoApi.rs.Exception;

public class CustomRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6419892706336268237L;
	private final String message;

	public CustomRuntimeException(String message) {
		super(message);
		this.message = message;
	}
	
	public CustomRuntimeException(String message, Throwable throwable ) {
		super(message, throwable);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
}
