package migration.core.db.relational.impl.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import migration.core.db.relational.AbstractDatabaseClient;
import migration.core.db.relational.IDatabaseClient;

public class MySqlDatabaseClient extends AbstractDatabaseClient implements IDatabaseClient {

	private String m_host;
	private int m_port;
	private String m_schema;
	private String m_username;
	private String m_password;

	public MySqlDatabaseClient(String host, int port, String schema, String username, String password) {
		m_host = host;
		m_port = port;
		m_schema = schema;
		m_username = username;
		m_password = password;
	}

	protected Connection openConnection() throws SQLException {
		String url = String.format("jdbc:mysql://%s:%d/%s", m_host, m_port, m_schema);
		Connection connection = DriverManager.getConnection(url, m_username, m_password);
		return connection;
	}
	
	@Override
	protected String getCatalog() {
		return null;
	}
	
	@Override
	protected String getSchema() {
		return null;
	}
}
