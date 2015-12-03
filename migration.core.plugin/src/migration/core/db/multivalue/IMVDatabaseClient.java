package migration.core.db.multivalue;

import java.util.List;

import asjava.uniobjects.UniSession;
import migration.core.model.mv.MVFile;

public interface IMVDatabaseClient {
	
	void createAccount(String accountName) throws MVProviderException;
	
	void deleteAccount(String accountName) throws MVProviderException;
	
	void createFile(String accountName, MVFile fileModel) throws MVProviderException;
	
	void createFile(UniSession session, MVFile fileModel) throws MVProviderException;
	
	void exportData(String accountName, String fileName, IMVMetadataProvider metadataProvider, IMVResultSet rs) throws MVProviderException;
	
	UniSession connect(String accountName) throws MVProviderException;
	
	void disconnect(UniSession session) throws MVProviderException;
	
	void exportData(UniSession session, String fileName, IMVMetadataProvider metadataProvider, IMVResultSet rs) throws MVProviderException;

	List<String> getAccounts() throws MVProviderException;
	
}
