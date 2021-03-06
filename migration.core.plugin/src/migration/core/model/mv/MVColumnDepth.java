package migration.core.model.mv;

public enum MVColumnDepth {
	singlevalue("S"),
	multivalue("M");
	
	private String m_value;

	private MVColumnDepth(String value) {
		m_value = value;
	}
	
	public String value() {
		return m_value;
	}
	
	public static MVColumnDepth resolve(String strValue) {
		if (strValue.equals("M")) {
			return multivalue;
		} else {
			return singlevalue;
		}
	}
}
