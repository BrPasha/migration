package migration.core.db.relational;

import migration.core.CoreException;

public class ProviderException extends CoreException {

	public ProviderException() {
		super();
	}
	
	public ProviderException(String message, String... params) {
		super(message, params);
	}
	
	public ProviderException(Throwable th) {
		super(th);
	}
	
	public ProviderException(Throwable th, String message, String... params) {
		super(th, message, params);
	}
	
	private static final long serialVersionUID = 1L;
}
