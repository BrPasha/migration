package migration.core.model.rdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RDBColumn {
	private String m_catalog;
	private String m_schema;
	private String m_table;
	private String m_name;
	private int m_dataType;
	private String m_typeName;
	private int m_columnSize;
	private int m_decimalDigits;
	private int m_numPrecRadix;
	private int m_nullable;
	private String m_remarks;
	private String m_columnDef;
	private int m_charOctetLength;
	private int m_ordinalPosition;
	private String m_nullableISO;
	private String m_scopeCatalog;
	private String m_scopeSchema;
	private String m_scopeTable;
	private short m_sourceDataType;
	private String m_autoincrement;
	private String m_generated;
	
	public RDBColumn() {
	}
	
	public RDBColumn(String catalog, String schema, String table, String name, 
			int dataType, String typeName, int columnSize, int decimalDigits,
			int numPrecRadix, int nullable, String remarks, String columnDef,
			int charOctetLength, int ordinalPosition, String nullableISO,
			String scopeCatalog, String scopeSchema, String scopeTable,
			short sourceDataType, String autoincrement, String generated) {
		m_catalog = catalog;
		m_schema = schema;
		m_table = table;
		m_name = name;
		m_dataType = dataType;
		m_typeName = typeName;
		m_columnSize = columnSize;
		m_decimalDigits = decimalDigits;
		m_numPrecRadix = numPrecRadix;
		m_nullable = nullable;
		m_remarks = remarks;
		m_columnDef = columnDef;
		m_charOctetLength = charOctetLength;
		m_ordinalPosition = ordinalPosition;
		m_nullableISO = nullableISO;
		m_scopeCatalog = scopeCatalog;
		m_scopeSchema = scopeSchema;
		m_scopeTable = scopeTable;
		m_sourceDataType = sourceDataType;
		m_autoincrement = autoincrement;
		m_generated = generated;
	}

	@JsonProperty("autoincrement")
	public String getAutoincrement() {
		return m_autoincrement;
	}

	@JsonProperty("catalog")
	public String getCatalog() {
		return m_catalog;
	}

	@JsonProperty("charOctetLength")
	public int getCharOctetLength() {
		return m_charOctetLength;
	}

	@JsonProperty("columnDef")
	public String getColumnDef() {
		return m_columnDef;
	}

	@JsonProperty("columnSize")
	public int getColumnSize() {
		return m_columnSize;
	}

	@JsonProperty("dataType")
	public int getDataType() {
		return m_dataType;
	}

	@JsonProperty("decimalDigits")
	public int getDecimalDigits() {
		return m_decimalDigits;
	}

	@JsonProperty("generated")
	public String getGenerated() {
		return m_generated;
	}

	@JsonProperty("name")
	public String getName() {
		return m_name;
	}

	@JsonProperty("nullable")
	public int getNullable() {
		return m_nullable;
	}

	@JsonProperty("nullableISO")
	public String getNullableISO() {
		return m_nullableISO;
	}

	@JsonProperty("numPrecRadix")
	public int getNumPrecRadix() {
		return m_numPrecRadix;
	}

	@JsonProperty("ordinalPosition")
	public int getOrdinalPosition() {
		return m_ordinalPosition;
	}

	@JsonProperty("remarks")
	public String getRemarks() {
		return m_remarks;
	}

	@JsonProperty("schema")
	public String getSchema() {
		return m_schema;
	}

	@JsonProperty("scopeCatalog")
	public String getScopeCatalog() {
		return m_scopeCatalog;
	}

	@JsonProperty("scopeSchema")
	public String getScopeSchema() {
		return m_scopeSchema;
	}

	@JsonProperty("scopeTable")
	public String getScopeTable() {
		return m_scopeTable;
	}

	@JsonProperty("sourceDataType")
	public short getSourceDataType() {
		return m_sourceDataType;
	}

	@JsonProperty("table")
	public String getTable() {
		return m_table;
	}

	@JsonProperty("typeName")
	public String getTypeName() {
		return m_typeName;
	}

	@JsonProperty("autoincrement")
	public void setAutoincrement(String autoincrement) {
		m_autoincrement = autoincrement;
	}

	@JsonProperty("catalog")
	public void setCatalog(String catalog) {
		m_catalog = catalog;
	}

	@JsonProperty("charOctetLength")
	public void setCharOctetLength(int charOctetLength) {
		m_charOctetLength = charOctetLength;
	}

	@JsonProperty("columnDef")
	public void setColumnDef(String columnDef) {
		m_columnDef = columnDef;
	}

	@JsonProperty("columnSize")
	public void setColumnSize(int columnSize) {
		m_columnSize = columnSize;
	}

	@JsonProperty("dataType")
	public void setDataType(int dataType) {
		m_dataType = dataType;
	}

	@JsonProperty("decimalDigits")
	public void setDecimalDigits(int decimalDigits) {
		m_decimalDigits = decimalDigits;
	}

	@JsonProperty("generated")
	public void setGenerated(String generated) {
		m_generated = generated;
	}

	@JsonProperty("name")
	public void setName(String name) {
		m_name = name;
	}

	@JsonProperty("nullable")
	public void setNullable(int nullable) {
		m_nullable = nullable;
	}

	@JsonProperty("nullableISO")
	public void setNullableISO(String nullableISO) {
		m_nullableISO = nullableISO;
	}

	@JsonProperty("numPrecRadix")
	public void setNumPrecRadix(int numPrecRadix) {
		m_numPrecRadix = numPrecRadix;
	}

	@JsonProperty("ordinalPosition")
	public void setOrdinalPosition(int ordinalPosition) {
		m_ordinalPosition = ordinalPosition;
	}

	@JsonProperty("remarks")
	public void setRemarks(String remarks) {
		m_remarks = remarks;
	}

	@JsonProperty("schema")
	public void setSchema(String schema) {
		m_schema = schema;
	}

	@JsonProperty("scopeCatalog")
	public void setScopeCatalog(String scopeCatalog) {
		m_scopeCatalog = scopeCatalog;
	}

	@JsonProperty("scopeSchema")
	public void setScopeSchema(String scopeSchema) {
		m_scopeSchema = scopeSchema;
	}

	@JsonProperty("scopeTable")
	public void setScopeTable(String scopeTable) {
		m_scopeTable = scopeTable;
	}

	@JsonProperty("sourceDataType")
	public void setSourceDataType(short sourceDataType) {
		m_sourceDataType = sourceDataType;
	}

	@JsonProperty("table")
	public void setTable(String table) {
		m_table = table;
	}

	@JsonProperty("typeName")
	public void setTypeName(String typeName) {
		m_typeName = typeName;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_autoincrement == null) ? 0 : m_autoincrement.hashCode());
		result = prime * result + ((m_catalog == null) ? 0 : m_catalog.hashCode());
		result = prime * result + m_charOctetLength;
		result = prime * result + ((m_columnDef == null) ? 0 : m_columnDef.hashCode());
		result = prime * result + m_columnSize;
		result = prime * result + m_dataType;
		result = prime * result + m_decimalDigits;
		result = prime * result + ((m_generated == null) ? 0 : m_generated.hashCode());
		result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
		result = prime * result + m_nullable;
		result = prime * result + ((m_nullableISO == null) ? 0 : m_nullableISO.hashCode());
		result = prime * result + m_numPrecRadix;
		result = prime * result + m_ordinalPosition;
		result = prime * result + ((m_remarks == null) ? 0 : m_remarks.hashCode());
		result = prime * result + ((m_schema == null) ? 0 : m_schema.hashCode());
		result = prime * result + ((m_scopeCatalog == null) ? 0 : m_scopeCatalog.hashCode());
		result = prime * result + ((m_scopeSchema == null) ? 0 : m_scopeSchema.hashCode());
		result = prime * result + ((m_scopeTable == null) ? 0 : m_scopeTable.hashCode());
		result = prime * result + m_sourceDataType;
		result = prime * result + ((m_table == null) ? 0 : m_table.hashCode());
		result = prime * result + ((m_typeName == null) ? 0 : m_typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RDBColumn other = (RDBColumn) obj;
		if (m_autoincrement == null) {
			if (other.m_autoincrement != null)
				return false;
		} else if (!m_autoincrement.equals(other.m_autoincrement))
			return false;
		if (m_catalog == null) {
			if (other.m_catalog != null)
				return false;
		} else if (!m_catalog.equals(other.m_catalog))
			return false;
		if (m_charOctetLength != other.m_charOctetLength)
			return false;
		if (m_columnDef == null) {
			if (other.m_columnDef != null)
				return false;
		} else if (!m_columnDef.equals(other.m_columnDef))
			return false;
		if (m_columnSize != other.m_columnSize)
			return false;
		if (m_dataType != other.m_dataType)
			return false;
		if (m_decimalDigits != other.m_decimalDigits)
			return false;
		if (m_generated == null) {
			if (other.m_generated != null)
				return false;
		} else if (!m_generated.equals(other.m_generated))
			return false;
		if (m_name == null) {
			if (other.m_name != null)
				return false;
		} else if (!m_name.equals(other.m_name))
			return false;
		if (m_nullable != other.m_nullable)
			return false;
		if (m_nullableISO == null) {
			if (other.m_nullableISO != null)
				return false;
		} else if (!m_nullableISO.equals(other.m_nullableISO))
			return false;
		if (m_numPrecRadix != other.m_numPrecRadix)
			return false;
		if (m_ordinalPosition != other.m_ordinalPosition)
			return false;
		if (m_remarks == null) {
			if (other.m_remarks != null)
				return false;
		} else if (!m_remarks.equals(other.m_remarks))
			return false;
		if (m_schema == null) {
			if (other.m_schema != null)
				return false;
		} else if (!m_schema.equals(other.m_schema))
			return false;
		if (m_scopeCatalog == null) {
			if (other.m_scopeCatalog != null)
				return false;
		} else if (!m_scopeCatalog.equals(other.m_scopeCatalog))
			return false;
		if (m_scopeSchema == null) {
			if (other.m_scopeSchema != null)
				return false;
		} else if (!m_scopeSchema.equals(other.m_scopeSchema))
			return false;
		if (m_scopeTable == null) {
			if (other.m_scopeTable != null)
				return false;
		} else if (!m_scopeTable.equals(other.m_scopeTable))
			return false;
		if (m_sourceDataType != other.m_sourceDataType)
			return false;
		if (m_table == null) {
			if (other.m_table != null)
				return false;
		} else if (!m_table.equals(other.m_table))
			return false;
		if (m_typeName == null) {
			if (other.m_typeName != null)
				return false;
		} else if (!m_typeName.equals(other.m_typeName))
			return false;
		return true;
	}
}
