package migration.core.db.multivalue;

import migration.core.model.mv.MVColumnDepth;

public interface IMVMetadataProvider {
	MVColumnDepth getDepth(String fieldName);
	MVColumnDepth getDepth(int fieldIndex);
}
