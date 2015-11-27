package migration.core.db.multivalue;

import migration.core.model.mv.MVFile;

public interface IMVDatabaseClient {
	
	void createAccount(String accountName) throws MVProviderException;
	
	void deleteAccount(String accountName) throws MVProviderException;
	
	void createFile(String accountName, MVFile fileModel) throws MVProviderException;
	
	void exportData(String accountName, String fileName, IMVResultSet rs) throws MVProviderException;
	
}
