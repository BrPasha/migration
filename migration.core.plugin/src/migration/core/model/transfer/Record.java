package migration.core.model.transfer;

import asjava.uniclientlibs.UniDynArray;

public class Record {
	private String m_id;
	private UniDynArray m_data;
	
	public Record(String id, UniDynArray data) {
		m_id = id;
		m_data = data;
	}
	
	public UniDynArray getData() {
		return m_data;
	}
	
	public String getId() {
		return m_id;
	}
}
