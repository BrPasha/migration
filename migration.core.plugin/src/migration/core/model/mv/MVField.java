package migration.core.model.mv;

public class MVField {
	private String m_name;
	private String m_heading;
	private String m_depth;
	private String m_type;
	private String m_location;
	private String m_def;
	private String m_assoc;
	private String m_convCode;
	private String m_format;
	
	public MVField() {
	}
	
	public MVField(String name, String location, String type, String def, String convCode, String heading, String format, String depth, String assoc) {
		m_name = name;
		m_convCode = convCode;
		m_heading = heading;
		m_format = format;
		m_depth = depth;
		m_type = type;
		m_location = location;
		m_def = def;
		m_assoc = assoc;
	}
	
	public String getAssoc() {
		return m_assoc;
	}
	
	public String getDef() {
		return m_def;
	}
	
	public String getDepth() {
		return m_depth;
	}
	
	public String getHeading() {
		return m_heading;
	}
	
	public String getLocation() {
		return m_location;
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getType() {
		return m_type;
	}
	
	public void setAssoc(String assoc) {
		m_assoc = assoc;
	}
	
	public void setDef(String def) {
		m_def = def;
	}
	
	public void setDepth(String depth) {
		m_depth = depth;
	}
	
	public void setHeading(String heading) {
		m_heading = heading;
	}
	
	public void setLocation(String location) {
		m_location = location;
	}
	
	public void setName(String name) {
		m_name = name;
	}
	
	public void setType(String type) {
		m_type = type;
	}
	
	public String getConvCode() {
		return m_convCode;
	}
	
	public void setConvCode(String convCode) {
		m_convCode = convCode;
	}
	
	public String getFormat() {
		return m_format;
	}
	
	public void setFormat(String format) {
		m_format = format;
	}

	@Override
	public String toString() {
		return getName();
	}
}
