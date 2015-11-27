package migration.core.db.multivalue;

import asjava.uniclientlibs.UniDynArray;

public interface IMVResultSet {
	boolean next();
	String id();
	UniDynArray row();
}
