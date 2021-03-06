package migration.core.db.multivalue.impl.uv;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import asjava.uniclientlibs.UniDataSet;
import asjava.uniclientlibs.UniDynArray;
import asjava.uniclientlibs.UniException;
import asjava.uniclientlibs.UniTokens;
import asjava.uniobjects.UniDictionary;
import asjava.uniobjects.UniFile;
import asjava.uniobjects.UniJava;
import asjava.uniobjects.UniSession;
import asjava.uniobjects.UniSessionException;
import asjava.uniobjects.UniSubroutine;
import migration.core.db.multivalue.IMVDatabaseClient;
import migration.core.db.multivalue.IMVMetadataProvider;
import migration.core.db.multivalue.IMVResultSet;
import migration.core.db.multivalue.MVProviderException;
import migration.core.db.relational.ProviderException;
import migration.core.model.mv.MVField;
import migration.core.model.mv.MVFile;
import migration.core.model.transfer.Record;

public class UniVerseDatabaseClient implements IMVDatabaseClient {

	private static final int MAX_RECORDS = 50;
	private static final String DEFAULT_ACCOUNT = "UV";
	private static final String XTOOLSUB = "*XTOOLSUB";

	private String m_host;
	private int m_port;
	private String m_username;
	private String m_password;
	
	private UniJava m_uniJava;

	public UniVerseDatabaseClient(String host, int port, String username, String password) {
		m_host = host;
		m_port = port;
		m_username = username;
		m_password = password;
		m_uniJava = new UniJava();
	}
	
	private UniSession openSession() throws MVProviderException {
		try {
			UniSession session = m_uniJava.openSession();
			session.setDataSourceType("UNIVERSE");
			session.setHostName(m_host);
			session.setHostPort(m_port);
			session.setAccountPath(DEFAULT_ACCOUNT);
			session.setUserName(m_username);
			session.setPassword(m_password);
			session.setSessionEncoding("ISO-8859-1");
			session.connect();
			invokeXtoolsub(session, XTOOLSUBConstant.Initialize, "");
			invokeXtoolsub(session, XTOOLSUBConstant.LogtoAccount, DEFAULT_ACCOUNT);
			return session;
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		}
	}
	
	private void closeSession(UniSession session) throws MVProviderException {
		if (session != null) {
			try {
				m_uniJava.closeSession(session);
			} catch (UniSessionException ex) {
				throw new MVProviderException(ex);
			}
		}
	}
	
	private String getU2Home(UniSession session) throws UniException {
		return invokeXtoolsub(session, XTOOLSUBConstant.GetHome, "").extract(1).toString();
	}
	
	private UniDynArray invokeXtoolsub(UniSession session, XTOOLSUBConstant code, String param) throws UniException {
		UniSubroutine subroutine = session.subroutine(XTOOLSUB, 4);
		subroutine.setArg(0, code.getCode());
		subroutine.setArg(1, param);
		subroutine.setArg(2, "");
		subroutine.setArg(3, "");
		subroutine.call();
		UniDynArray outdata = subroutine.getArgDynArray(2);
		UniDynArray error = subroutine.getArgDynArray(3);
		if (!error.equals("0")) {
			throw new UniException(
					MessageFormat.format("*XTOOLSUB {0} with {1} returned: {2} - {3}", code, param, error, outdata),
					UniTokens.UVE_SUBERROR);
		}
		return outdata;
	}
	
	@Override
	public void createAccount(String accountName) throws MVProviderException {
		UniSession session = openSession();
		try {
			createAccount(session, accountName);
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		} finally {
			closeSession(session);
		}
	}
	
	@Override
	public void deleteAccount(String accountName) throws MVProviderException {
		UniSession session = openSession();
		try {
			invokeXtoolsub(session, XTOOLSUBConstant.DeleteAcct, accountName);
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		} finally {
			closeSession(session);
		}
	}
	
	@Override
	public List<String> getAccounts() throws MVProviderException {
		UniSession session = openSession();
		try {
			UniDynArray output = invokeXtoolsub(session, XTOOLSUBConstant.GetAccounts, "");
			List<String> result = new ArrayList<>();
			for (int i = 1; i <= output.dcount(); i++) {
				String account = output.extract(i, 1).toString();
				result.add(account);
			}
			return result;
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		} finally {
			closeSession(session);
		}
	}
	
	private void createAccount(UniSession session, String accountName) throws UniException {
		UniDynArray params = session.dynArray("");
		params.replace(1, getU2Home(session) + "\\" + accountName);
		params.replace(2, accountName);
		params.replace(3, "1");
		UniDynArray flavor = session.dynArray("");
		flavor.replace(2, "NEWACC");
		flavor.replace(3, "0");
		params.replace(4, flavor);
		invokeXtoolsub(session, XTOOLSUBConstant.NewAcct, params.toString());
	}
	
	private void logtoAccount(UniSession session, String accountName) throws UniException {
		UniDynArray params = session.dynArray(accountName);
		invokeXtoolsub(session, XTOOLSUBConstant.LogtoAccount, params.toString());
	}

	@Override
	public void createFile(String accountName, MVFile fileModel) throws MVProviderException {
		UniSession session = openSession();
		try {
			logtoAccount(session, accountName);
			createFile(session, fileModel);
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		} finally {
			closeSession(session);
		}
	}

	@Override
	public void createFile(UniSession session, MVFile fileModel) throws MVProviderException {
		try {
			UniDynArray param = session.dynArray("");
			param.replace(1, fileModel.getName()); // File Name
			param.replace(2, "DYNAMIC"); // File Type
			param.replace(3, "1"); // Minimum Modulus
			param.replace(4, "1"); // Group Size
			param.replace(5, "80"); // Large Record
			param.replace(6, "80"); // Split Load
			param.replace(7, "50"); // Merge Load
			param.replace(8, "GENERAL"); // Hashing Algorithm
			param.replace(9, "1"); // 64-bit
			invokeXtoolsub(session, XTOOLSUBConstant.CreateFile, param.toString());
			UniDictionary dict = session.openDict(fileModel.getName());
			try {
				for (MVField fieldModel : fileModel.getFields()) {
					UniDynArray fieldDescription = session.dynArray("");
					fieldDescription.replace(1, fieldModel.getType());
					fieldDescription.replace(2, fieldModel.getType().startsWith("D") ? fieldModel.getLocation() : fieldModel.getDef());
					fieldDescription.replace(3, fieldModel.getConvCode());
					fieldDescription.replace(4, fieldModel.getHeading());
					fieldDescription.replace(5, fieldModel.getFormat());
					fieldDescription.replace(6, fieldModel.getDepth());
					fieldDescription.replace(7, fieldModel.getAssoc());
					dict.write(fieldModel.getName(), fieldDescription);
				}
			} finally {
				dict.close();
			}
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		}
	}

	@Override
	public void exportData(String accountName, String fileName, IMVMetadataProvider metadataProvider, IMVResultSet rs) throws MVProviderException {
		UniSession session = openSession();
		try {
			logtoAccount(session, accountName);
			UniFile file = session.openFile(fileName);
			try {
				UniDataSet dataSet = session.dataSet();
				Record record = null;
				int counter = 0;
				while (counter < 50 && (record = rs.nextRecord(session, metadataProvider)) != null) {
					dataSet.append(record.getId(), record.getData());
					if (fileName.equals("customer") && counter++ < 0) {
						break;
					}
				}
				file.write(dataSet);
			} finally {
				file.close();
			}
		} catch (ProviderException ex) {
			throw new MVProviderException(ex);
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		} finally {
			closeSession(session);
		}
	}

	@Override
	public UniSession connect(String accountName) throws MVProviderException {
		UniSession session = openSession();
		try {
			logtoAccount(session, accountName);
			return session;
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		}
	}
	
	@Override
	public void disconnect(UniSession session) throws MVProviderException {
		closeSession(session);
	}
	
	@Override
	public void exportData(UniSession session, String fileName, IMVMetadataProvider metadataProvider, IMVResultSet rs)
			throws MVProviderException {
		try {
			UniFile file = session.openFile(fileName);
			try {
				UniDataSet dataSet = session.dataSet();
				Record record = null;
				while ((record = rs.nextRecord(session, metadataProvider)) != null) {
					dataSet.append(record.getId(), record.getData());
				}
				file.write(dataSet);
			} finally {
				file.close();
			}
		} catch (ProviderException ex) {
			throw new MVProviderException(ex);
		} catch (UniException ex) {
			throw new MVProviderException(ex);
		}
	}
	
}
