package migration.core.db.multivalue;

import asjava.uniobjects.UniSession;
import migration.core.db.relational.ProviderException;
import migration.core.model.transfer.Record;

public interface IMVResultSet extends AutoCloseable {
	Record nextRecord(UniSession session, IMVMetadataProvider metadataProvider) throws ProviderException;
}
