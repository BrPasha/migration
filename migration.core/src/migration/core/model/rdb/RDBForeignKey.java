package migration.core.model.rdb;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import migration.core.util.Pair;

public class RDBForeignKey {
	private String m_pkCatalog;
	private String m_pkSchema;
	private String m_pkTable;
	private String m_pkName;
	
	private String m_fkCatalog;
	private String m_fkSchema;
	private String m_fkTable;
	private String m_fkName;
	
	private List<Pair<String, String>> m_fkToPk;
	
	private short m_updateRule;
	private short m_deleteRule;
	private short m_deferrability;
	
	public RDBForeignKey(String pkcatalog, String pkschema, String pktable, String pkname,
			String fkcatalog, String fkschema, String fktable, String fkname, 
			short updateRule, short deleteRule, short deferrability) {
		m_pkCatalog = pkcatalog;
		m_pkSchema = pkschema;
		m_pkTable = pktable;
		m_pkName = pkname;
		m_fkCatalog = fkcatalog;
		m_fkSchema = fkschema;
		m_fkTable = fktable;
		m_fkName = fkname;
		m_fkToPk = new ArrayList<>();
		m_updateRule = updateRule;
		m_deleteRule = deleteRule;
		m_deferrability = deferrability;
	}
	
	public short getDeferrability() {
		return m_deferrability;
	}
	
	public short getDeleteRule() {
		return m_deleteRule;
	}
	
	public String getFkCatalog() {
		return m_fkCatalog;
	}
	
	public List<Pair<String, String>> getFkColumns() {
		return m_fkToPk;
	}
	
	public void setFkToPk(Collection<Pair<String, String>> fkToPk) {
		m_fkToPk = new ArrayList<>(fkToPk);
	}
	
	public String getFkName() {
		return m_fkName;
	}
	
	public String getFkSchema() {
		return m_fkSchema;
	}
	
	public String getFkTable() {
		return m_fkTable;
	}
	
	public String getPkCatalog() {
		return m_pkCatalog;
	}
	
	public String getPkName() {
		return m_pkName;
	}
	
	public String getPkSchema() {
		return m_pkSchema;
	}
	
	public String getPkTable() {
		return m_pkTable;
	}
	
	public short getUpdateRule() {
		return m_updateRule;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("{0}-->{1}:{2}", 
				getPkTable(), 
				getFkTable(), 
				m_fkToPk.stream().map(p -> p.first() + "+" + p.second()).collect(Collectors.toList()));
	}
}
