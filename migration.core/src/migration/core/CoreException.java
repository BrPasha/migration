package migration.core;

import java.text.MessageFormat;

public class CoreException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CoreException() {
		super();
	}
	
	public CoreException(String message, String... params) {
		super(MessageFormat.format(message, params));
	}
	
	public CoreException(Throwable th) {
		super(th);
	}
	
	public CoreException(Throwable th, String message, String... params) {
		super(MessageFormat.format(message, params), th);
	}
}
