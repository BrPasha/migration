package migration.core.db.multivalue.impl.uv;

public enum XTOOLSUBConstant {
	Initialize(0),
	GetAccounts(1),
	LogtoAccount(2),
	GetFiles(3),
	GetDict(4),
	OSBrowse(5),
	GetPlatform(28),
	DeleteFile(29),
	NewAcct(30),
	DeleteAcct(31),
	CreateFile(32),
	GetHome(63);
	
	private int m_code;

	private XTOOLSUBConstant(int code) {
		m_code = code;
	}
	
	public int getCode() {
		return m_code;
	}
}
