package migration.core.db.multivalue;

import migration.core.model.mv.MVFile;

public interface IMVDatabaseClient {
	
	void createAccount(String accountName) throws MVProviderException;
	
	void deleteAccount(String accountName) throws MVProviderException;
	
	void createFile(MVFile fileModel) throws MVProviderException;
	
}
