package migration.core.db.multivalue;

import migration.core.db.relational.ProviderException;
import migration.core.model.transfer.Record;

public interface IMVResultSet extends AutoCloseable {
	Record nextRecord() throws ProviderException;
}
