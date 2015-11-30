package migration.core.db.relational;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class ConnectionHoldingResultSet implements AutoCloseable, ResultSet {

	private ResultSet m_rs;
	private Connection m_connection;
	private Statement m_stmt;

	public ConnectionHoldingResultSet(ResultSet rs, Statement stmt, Connection connection) {
		m_rs = rs;
		m_stmt = stmt;
		m_connection = connection;
	}
	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return m_rs.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return m_rs.isWrapperFor(iface);
	}

	@Override
	public boolean next() throws SQLException {
		return m_rs.next();
	}

	@Override
	public boolean wasNull() throws SQLException {
		return m_rs.wasNull();
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		return m_rs.getString(columnIndex);
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		return m_rs.getBoolean(columnIndex);
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		return m_rs.getByte(columnIndex);
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		return m_rs.getShort(columnIndex);
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		return m_rs.getInt(columnIndex);
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		return m_rs.getLong(columnIndex);
	}

	@Override
	public void close() throws SQLException {
		try {
			m_rs.close();
		} finally {
			m_stmt.close();
			m_connection.close();
		}
	}

	public float getFloat(int columnIndex) throws SQLException {
		return m_rs.getFloat(columnIndex);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return m_rs.getDouble(columnIndex);
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		return m_rs.getBigDecimal(columnIndex, scale);
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		return m_rs.getBytes(columnIndex);
	}

	public Date getDate(int columnIndex) throws SQLException {
		return m_rs.getDate(columnIndex);
	}

	public Time getTime(int columnIndex) throws SQLException {
		return m_rs.getTime(columnIndex);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return m_rs.getTimestamp(columnIndex);
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return m_rs.getAsciiStream(columnIndex);
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return m_rs.getUnicodeStream(columnIndex);
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return m_rs.getBinaryStream(columnIndex);
	}

	public String getString(String columnLabel) throws SQLException {
		return m_rs.getString(columnLabel);
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		return m_rs.getBoolean(columnLabel);
	}

	public byte getByte(String columnLabel) throws SQLException {
		return m_rs.getByte(columnLabel);
	}

	public short getShort(String columnLabel) throws SQLException {
		return m_rs.getShort(columnLabel);
	}

	public int getInt(String columnLabel) throws SQLException {
		return m_rs.getInt(columnLabel);
	}

	public long getLong(String columnLabel) throws SQLException {
		return m_rs.getLong(columnLabel);
	}

	public float getFloat(String columnLabel) throws SQLException {
		return m_rs.getFloat(columnLabel);
	}

	public double getDouble(String columnLabel) throws SQLException {
		return m_rs.getDouble(columnLabel);
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		return m_rs.getBigDecimal(columnLabel, scale);
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		return m_rs.getBytes(columnLabel);
	}

	public Date getDate(String columnLabel) throws SQLException {
		return m_rs.getDate(columnLabel);
	}

	public Time getTime(String columnLabel) throws SQLException {
		return m_rs.getTime(columnLabel);
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return m_rs.getTimestamp(columnLabel);
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return m_rs.getAsciiStream(columnLabel);
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return m_rs.getUnicodeStream(columnLabel);
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return m_rs.getBinaryStream(columnLabel);
	}

	public SQLWarning getWarnings() throws SQLException {
		return m_rs.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		m_rs.clearWarnings();
	}

	public String getCursorName() throws SQLException {
		return m_rs.getCursorName();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return m_rs.getMetaData();
	}

	public Object getObject(int columnIndex) throws SQLException {
		return m_rs.getObject(columnIndex);
	}

	public Object getObject(String columnLabel) throws SQLException {
		return m_rs.getObject(columnLabel);
	}

	public int findColumn(String columnLabel) throws SQLException {
		return m_rs.findColumn(columnLabel);
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return m_rs.getCharacterStream(columnIndex);
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return m_rs.getCharacterStream(columnLabel);
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return m_rs.getBigDecimal(columnIndex);
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return m_rs.getBigDecimal(columnLabel);
	}

	public boolean isBeforeFirst() throws SQLException {
		return m_rs.isBeforeFirst();
	}

	public boolean isAfterLast() throws SQLException {
		return m_rs.isAfterLast();
	}

	public boolean isFirst() throws SQLException {
		return m_rs.isFirst();
	}

	public boolean isLast() throws SQLException {
		return m_rs.isLast();
	}

	public void beforeFirst() throws SQLException {
		m_rs.beforeFirst();
	}

	public void afterLast() throws SQLException {
		m_rs.afterLast();
	}

	public boolean first() throws SQLException {
		return m_rs.first();
	}

	public boolean last() throws SQLException {
		return m_rs.last();
	}

	public int getRow() throws SQLException {
		return m_rs.getRow();
	}

	public boolean absolute(int row) throws SQLException {
		return m_rs.absolute(row);
	}

	public boolean relative(int rows) throws SQLException {
		return m_rs.relative(rows);
	}

	public boolean previous() throws SQLException {
		return m_rs.previous();
	}

	public void setFetchDirection(int direction) throws SQLException {
		m_rs.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		return m_rs.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		m_rs.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException {
		return m_rs.getFetchSize();
	}

	public int getType() throws SQLException {
		return m_rs.getType();
	}

	public int getConcurrency() throws SQLException {
		return m_rs.getConcurrency();
	}

	public boolean rowUpdated() throws SQLException {
		return m_rs.rowUpdated();
	}

	public boolean rowInserted() throws SQLException {
		return m_rs.rowInserted();
	}

	public boolean rowDeleted() throws SQLException {
		return m_rs.rowDeleted();
	}

	public void updateNull(int columnIndex) throws SQLException {
		m_rs.updateNull(columnIndex);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		m_rs.updateBoolean(columnIndex, x);
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		m_rs.updateByte(columnIndex, x);
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		m_rs.updateShort(columnIndex, x);
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		m_rs.updateInt(columnIndex, x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		m_rs.updateLong(columnIndex, x);
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		m_rs.updateFloat(columnIndex, x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		m_rs.updateDouble(columnIndex, x);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		m_rs.updateBigDecimal(columnIndex, x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		m_rs.updateString(columnIndex, x);
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		m_rs.updateBytes(columnIndex, x);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		m_rs.updateDate(columnIndex, x);
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		m_rs.updateTime(columnIndex, x);
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		m_rs.updateTimestamp(columnIndex, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		m_rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		m_rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		m_rs.updateCharacterStream(columnIndex, x, length);
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		m_rs.updateObject(columnIndex, x, scaleOrLength);
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		m_rs.updateObject(columnIndex, x);
	}

	public void updateNull(String columnLabel) throws SQLException {
		m_rs.updateNull(columnLabel);
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		m_rs.updateBoolean(columnLabel, x);
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		m_rs.updateByte(columnLabel, x);
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		m_rs.updateShort(columnLabel, x);
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		m_rs.updateInt(columnLabel, x);
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		m_rs.updateLong(columnLabel, x);
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		m_rs.updateFloat(columnLabel, x);
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		m_rs.updateDouble(columnLabel, x);
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		m_rs.updateBigDecimal(columnLabel, x);
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		m_rs.updateString(columnLabel, x);
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		m_rs.updateBytes(columnLabel, x);
	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		m_rs.updateDate(columnLabel, x);
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		m_rs.updateTime(columnLabel, x);
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		m_rs.updateTimestamp(columnLabel, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		m_rs.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		m_rs.updateBinaryStream(columnLabel, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		m_rs.updateCharacterStream(columnLabel, reader, length);
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		m_rs.updateObject(columnLabel, x, scaleOrLength);
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		m_rs.updateObject(columnLabel, x);
	}

	public void insertRow() throws SQLException {
		m_rs.insertRow();
	}

	public void updateRow() throws SQLException {
		m_rs.updateRow();
	}

	public void deleteRow() throws SQLException {
		m_rs.deleteRow();
	}

	public void refreshRow() throws SQLException {
		m_rs.refreshRow();
	}

	public void cancelRowUpdates() throws SQLException {
		m_rs.cancelRowUpdates();
	}

	public void moveToInsertRow() throws SQLException {
		m_rs.moveToInsertRow();
	}

	public void moveToCurrentRow() throws SQLException {
		m_rs.moveToCurrentRow();
	}

	public Statement getStatement() throws SQLException {
		return m_rs.getStatement();
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		return m_rs.getObject(columnIndex, map);
	}

	public Ref getRef(int columnIndex) throws SQLException {
		return m_rs.getRef(columnIndex);
	}

	public Blob getBlob(int columnIndex) throws SQLException {
		return m_rs.getBlob(columnIndex);
	}

	public Clob getClob(int columnIndex) throws SQLException {
		return m_rs.getClob(columnIndex);
	}

	public Array getArray(int columnIndex) throws SQLException {
		return m_rs.getArray(columnIndex);
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		return m_rs.getObject(columnLabel, map);
	}

	public Ref getRef(String columnLabel) throws SQLException {
		return m_rs.getRef(columnLabel);
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		return m_rs.getBlob(columnLabel);
	}

	public Clob getClob(String columnLabel) throws SQLException {
		return m_rs.getClob(columnLabel);
	}

	public Array getArray(String columnLabel) throws SQLException {
		return m_rs.getArray(columnLabel);
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return m_rs.getDate(columnIndex, cal);
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return m_rs.getDate(columnLabel, cal);
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return m_rs.getTime(columnIndex, cal);
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return m_rs.getTime(columnLabel, cal);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		return m_rs.getTimestamp(columnIndex, cal);
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		return m_rs.getTimestamp(columnLabel, cal);
	}

	public URL getURL(int columnIndex) throws SQLException {
		return m_rs.getURL(columnIndex);
	}

	public URL getURL(String columnLabel) throws SQLException {
		return m_rs.getURL(columnLabel);
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		m_rs.updateRef(columnIndex, x);
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		m_rs.updateRef(columnLabel, x);
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		m_rs.updateBlob(columnIndex, x);
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		m_rs.updateBlob(columnLabel, x);
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		m_rs.updateClob(columnIndex, x);
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		m_rs.updateClob(columnLabel, x);
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		m_rs.updateArray(columnIndex, x);
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		m_rs.updateArray(columnLabel, x);
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		return m_rs.getRowId(columnIndex);
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		return m_rs.getRowId(columnLabel);
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		m_rs.updateRowId(columnIndex, x);
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		m_rs.updateRowId(columnLabel, x);
	}

	public int getHoldability() throws SQLException {
		return m_rs.getHoldability();
	}

	public boolean isClosed() throws SQLException {
		return m_rs.isClosed();
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		m_rs.updateNString(columnIndex, nString);
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		m_rs.updateNString(columnLabel, nString);
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		m_rs.updateNClob(columnIndex, nClob);
	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		m_rs.updateNClob(columnLabel, nClob);
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		return m_rs.getNClob(columnIndex);
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		return m_rs.getNClob(columnLabel);
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return m_rs.getSQLXML(columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return m_rs.getSQLXML(columnLabel);
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		m_rs.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		m_rs.updateSQLXML(columnLabel, xmlObject);
	}

	public String getNString(int columnIndex) throws SQLException {
		return m_rs.getNString(columnIndex);
	}

	public String getNString(String columnLabel) throws SQLException {
		return m_rs.getNString(columnLabel);
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return m_rs.getNCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return m_rs.getNCharacterStream(columnLabel);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		m_rs.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		m_rs.updateNCharacterStream(columnLabel, reader, length);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		m_rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		m_rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		m_rs.updateCharacterStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		m_rs.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		m_rs.updateBinaryStream(columnLabel, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		m_rs.updateCharacterStream(columnLabel, reader, length);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		m_rs.updateBlob(columnIndex, inputStream, length);
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		m_rs.updateBlob(columnLabel, inputStream, length);
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		m_rs.updateClob(columnIndex, reader, length);
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		m_rs.updateClob(columnLabel, reader, length);
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		m_rs.updateNClob(columnIndex, reader, length);
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		m_rs.updateNClob(columnLabel, reader, length);
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		m_rs.updateNCharacterStream(columnIndex, x);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		m_rs.updateNCharacterStream(columnLabel, reader);
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		m_rs.updateAsciiStream(columnIndex, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		m_rs.updateBinaryStream(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		m_rs.updateCharacterStream(columnIndex, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		m_rs.updateAsciiStream(columnLabel, x);
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		m_rs.updateBinaryStream(columnLabel, x);
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		m_rs.updateCharacterStream(columnLabel, reader);
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		m_rs.updateBlob(columnIndex, inputStream);
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		m_rs.updateBlob(columnLabel, inputStream);
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		m_rs.updateClob(columnIndex, reader);
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		m_rs.updateClob(columnLabel, reader);
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		m_rs.updateNClob(columnIndex, reader);
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		m_rs.updateNClob(columnLabel, reader);
	}

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		return m_rs.getObject(columnIndex, type);
	}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		return m_rs.getObject(columnLabel, type);
	}

	public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength)
			throws SQLException {
		m_rs.updateObject(columnIndex, x, targetSqlType, scaleOrLength);
	}

	public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength)
			throws SQLException {
		m_rs.updateObject(columnLabel, x, targetSqlType, scaleOrLength);
	}

	public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
		m_rs.updateObject(columnIndex, x, targetSqlType);
	}

	public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
		m_rs.updateObject(columnLabel, x, targetSqlType);
	}

}
