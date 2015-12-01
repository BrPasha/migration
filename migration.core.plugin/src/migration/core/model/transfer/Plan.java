package migration.core.model.transfer;

import java.util.Iterator;
import java.util.List;

import migration.core.db.relational.IDatabaseClient;
import migration.core.model.mv.MVFile;

public class Plan {
	
	private List<Transfer> m_transfers;
	private Iterator<Transfer> m_transfersIterator;
	private Transfer m_currentTransfer;
	private IDatabaseClient m_databaseClient;

	public Plan(List<Transfer> transfers, IDatabaseClient databaseClient) {
		m_transfers = transfers;
		m_databaseClient = databaseClient;
		m_transfersIterator = m_transfers.iterator();
	}
	
	public boolean next() {
		if (m_transfersIterator.hasNext()) {
			m_currentTransfer = m_transfersIterator.next();
			return true;
		}
		return false;
	}
	
	public MVFile getStructure() {
		return m_currentTransfer.constructMVFile();
	}
	
	public Data getData() {
		return new Data(m_currentTransfer, m_databaseClient);
	}
	
}
