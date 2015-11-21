package migration.core.db.multivalue;

import migration.core.CoreException;

public class MVProviderException extends CoreException {

	public MVProviderException() {
		super();
	}
	
	public MVProviderException(String message, String... params) {
		super(message, params);
	}
	
	public MVProviderException(Throwable th) {
		super(th);
	}
	
	public MVProviderException(Throwable th, String message, String... params) {
		super(th, message, params);
	}
	
	private static final long serialVersionUID = 1L;
}
