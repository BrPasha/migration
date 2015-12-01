package migration.core.db.multivalue;

import asjava.uniclientlibs.UniDynArray;
import migration.core.db.relational.ProviderException;

public interface IMVResultSet {
	boolean next() throws ProviderException;
	String recordId() throws ProviderException;
	UniDynArray record() throws ProviderException;
}
