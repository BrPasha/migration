package migration.core.db.relational;

import java.sql.ResultSet;
import java.util.List;

import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBTable;

public interface IDatabaseClient {
	
	List<RDBTable> getTables() throws ProviderException;
	
	List<RDBRelation> getRelations() throws ProviderException;

	ResultSet executeQuery(String sql) throws ProviderException;
	
}
