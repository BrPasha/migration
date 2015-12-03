package editors.database;

import migration.core.model.rdb.RDBRelation;

public interface JoinListener {
	void onApply(RDBRelation relation);
}
