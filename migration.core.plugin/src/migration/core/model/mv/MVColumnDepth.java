package migration.core.model.mv;

public enum MVColumnDepth {
	singlevalue("S"),
	multivalue("M");
	
	private String m_value;

	private MVColumnDepth(String value) {
		m_value = value;
	}
	
	public String getValue() {
		return m_value;
	}
}
