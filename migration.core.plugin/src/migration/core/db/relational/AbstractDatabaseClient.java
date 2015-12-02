package migration.core.db.relational;

import static migration.core.util.Pair.pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import migration.core.model.rdb.RDBColumn;
import migration.core.model.rdb.RDBForeignKey;
import migration.core.model.rdb.RDBPrimaryKey;
import migration.core.model.rdb.RDBRelation;
import migration.core.model.rdb.RDBRelationType;
import migration.core.model.rdb.RDBTable;
import migration.core.util.Pair;

public abstract class AbstractDatabaseClient implements IDatabaseClient {

	protected abstract Connection openConnection() throws SQLException;
	protected abstract String getCatalog();
	protected abstract String getSchema();
	
	@Override
	public List<RDBTable> getTables() throws ProviderException {
		try (Connection connection = openConnection()) {
			List<RDBTable> tables = getTables(connection);
			for (RDBTable table : tables) {
				table.setPrimaryKey(getPrimaryKey(connection, table.getName()));
			}
			return tables;
		} catch (SQLException ex) {
			throw new ProviderException(ex, "Cannot retrieve tables");
		}
	}

	private List<RDBTable> getTables(Connection connection) throws SQLException {
		List<RDBTable> result = new ArrayList<>();
		try (ResultSet rs = connection.getMetaData().getTables(getCatalog(), getSchema(), null, new String[] {"TABLE"})) {
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				String remarks = rs.getString("REMARKS");
				List<RDBColumn> columns = getColumns(connection, tableName);
				RDBTable table = new RDBTable(tableName, remarks, columns);
				result.add(table);
			}
		}
		return result;
	}

	private List<RDBColumn> getColumns(Connection connection, String tableName) throws SQLException {
		List<RDBColumn> result = new ArrayList<>();
		try (ResultSet rs = connection.getMetaData().getColumns(getCatalog(), getSchema(), tableName, null)) {
			while (rs.next()) {
				String tableCat = rs.getString("TABLE_CAT");
				String tableSchem = rs.getString("TABLE_SCHEM");
				String table = rs.getString("TABLE_NAME");
				String name = rs.getString("COLUMN_NAME");
				int dataType = rs.getInt("DATA_TYPE");
				String typeName = rs.getString("TYPE_NAME");
				int columnSize = rs.getInt("COLUMN_SIZE");
				int decimalDigits = rs.getInt("DECIMAL_DIGITS");
				if (rs.wasNull()) {
					decimalDigits = -1;
				}
				int numPrecRadix = rs.getInt("NUM_PREC_RADIX");
				int nullable = rs.getInt("NULLABLE");
				String remarks = rs.getString("REMARKS");
				String columnDef = rs.getString("COLUMN_DEF");
				int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
				int ordinalPosition = rs.getInt("ORDINAL_POSITION");
				String nullableISO = rs.getString("IS_NULLABLE");
				String scopeCatalog = rs.getString("SCOPE_CATALOG");
				String scopeSchema = rs.getString("SCOPE_SCHEMA");
				String scopeTable = rs.getString("SCOPE_TABLE");
				short sourceDataType = rs.getShort("SOURCE_DATA_TYPE");
				if (rs.wasNull()) {
					sourceDataType = -1;
				}
				String autoincrement = rs.getString("IS_AUTOINCREMENT");
				String generated = rs.getString("IS_GENERATEDCOLUMN");
				if (typeName.equals("GEOMETRY")
						|| typeName.equals("BLOB")) {
					typeName = "BLOB";
					dataType = Types.BLOB;
				}
				RDBColumn column = new RDBColumn(
						tableCat, 
						tableSchem, 
						table, 
						name, 
						dataType, 
						typeName, 
						columnSize, 
						decimalDigits, 
						numPrecRadix, 
						nullable, 
						remarks, 
						columnDef, 
						charOctetLength, 
						ordinalPosition,
						nullableISO, 
						scopeCatalog, 
						scopeSchema, 
						scopeTable, 
						sourceDataType, 
						autoincrement, 
						generated);
				result.add(column);
			}
		}
		return result;
	}



	@Override
	public List<RDBRelation> getRelations() throws ProviderException {
		List<RDBRelation> relations = new ArrayList<>();
		try (Connection connection = openConnection()) {
			List<RDBTable> tables = getTables(connection);
			for (RDBTable table : tables) {
				List<RDBForeignKey> foreignKeys = getForeignKeys(connection, table.getName());
				List<RDBRelation> tableRelations = foreignKeys.stream()
					.flatMap(key -> key.getFkColumns().stream()
							.map(p -> new RDBRelation(key.getPkTable(), p.second(), key.getFkTable(), p.first(), RDBRelationType.primaryToForeign)))
					.collect(Collectors.toList());
				relations.addAll(tableRelations);
			}
		} catch (SQLException ex) {
			throw new ProviderException(ex, "Cannot retrieve relations");
		}
		return relations;
	}

	private RDBPrimaryKey getPrimaryKey(Connection connection, String table) throws ProviderException {
		RDBPrimaryKey result = null;
		try (ResultSet rs = connection.getMetaData().getPrimaryKeys(getCatalog(), getSchema(), table)) {
			TreeMap<Integer, String> columns = new TreeMap<>();
			String tableCatalog = null;
			String tableSchema = null;
			String primaryKeyName = null;
			while (rs.next()) {
				tableCatalog = rs.getString("TABLE_CAT");
				tableSchema = rs.getString("TABLE_SCHEM");
				String columnName = rs.getString("COLUMN_NAME");
				short keySeq = rs.getShort("KEY_SEQ");
				primaryKeyName = rs.getString("PK_NAME");
				columns.put((int) keySeq, columnName);
			}
			result = new RDBPrimaryKey(tableCatalog, tableSchema, table, primaryKeyName, new ArrayList<>(columns.values()));
		} catch (SQLException ex) {
			throw new ProviderException(ex, "Cannot retrieve primary keys for table {0}", table);
		}
		return result;
	}
	
	private List<RDBForeignKey> getForeignKeys(Connection connection, String table) throws ProviderException {
		List<RDBForeignKey> foreignKeys = new ArrayList<>();
		try (ResultSet rs = connection.getMetaData().getExportedKeys(getCatalog(), getSchema(), table)) {
			Map<RDBForeignKey, TreeMap<Integer, Pair<String, String>>> keyToColumns = new HashMap<>();
			while (rs.next()) {
				String pkTableCatalog = rs.getString("PKTABLE_CAT");
				String pkTableSchema = rs.getString("PKTABLE_SCHEM");
				String pkTableName = rs.getString("PKTABLE_NAME");
				String pkColumnName = rs.getString("PKCOLUMN_NAME");
				String fkTableCatalog = rs.getString("FKTABLE_CAT");
				String fkTableSchema = rs.getString("FKTABLE_SCHEM");
				String fkTableName = rs.getString("FKTABLE_NAME");
				String fkColumnName = rs.getString("FKCOLUMN_NAME");
				short keySeq = rs.getShort("KEY_SEQ");
				short updateRule = rs.getShort("UPDATE_RULE");
				short deleteRule = rs.getShort("DELETE_RULE");
				String fkName = rs.getString("FK_NAME");
				String pkName = rs.getString("PK_NAME");
				short deferrability = rs.getShort("DEFERRABILITY");
				Optional<RDBForeignKey> foundKey = keyToColumns.keySet()
						.stream()
						.filter(key -> {
							return Objects.equals(pkTableCatalog, key.getPkCatalog())
									&& Objects.equals(pkName, key.getPkName())
									&& Objects.equals(pkTableSchema, key.getPkSchema())
									&& Objects.equals(pkTableName, key.getPkTable())
									&& Objects.equals(fkTableCatalog, key.getFkCatalog())
									&& Objects.equals(fkName, key.getFkName())
									&& Objects.equals(fkTableSchema, key.getFkSchema())
									&& Objects.equals(fkTableName, key.getFkTable())
									&& Objects.equals(updateRule, key.getUpdateRule())
									&& Objects.equals(deleteRule, key.getDeleteRule())
									&& Objects.equals(deferrability, key.getDeferrability());
						})
						.findFirst();
				if (foundKey.isPresent()) {
					keyToColumns.get(foundKey.get()).put((int) keySeq, pair(fkColumnName, pkColumnName));
				} else {
					TreeMap<Integer, Pair<String, String>> seqMap = new TreeMap<>();
					seqMap.put((int) keySeq, pair(fkColumnName, pkColumnName));
					RDBForeignKey foreignKey = new RDBForeignKey(
							pkTableCatalog, pkTableSchema, pkTableName, pkName,
							fkTableCatalog, fkTableSchema, fkTableName, fkName, 
							updateRule, deleteRule, deferrability);
					keyToColumns.put(foreignKey, seqMap);
				}
			}
			keyToColumns.entrySet().forEach(e -> e.getKey().setFkToPk(e.getValue().values()));
			foreignKeys.addAll(keyToColumns.keySet());
		} catch (SQLException ex) {
			throw new ProviderException(ex, "Cannot retrieve foreign keys for table {0}", table);
		}
		return foreignKeys;
	}

	@Override
	public ResultSet executeQuery(String sql) throws ProviderException {
		try {
			Connection conn = openConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			return new ConnectionHoldingResultSet(stmt.executeQuery(), stmt, conn);
		} catch (SQLException ex) {
			throw new ProviderException(ex);
		}
	}
}
