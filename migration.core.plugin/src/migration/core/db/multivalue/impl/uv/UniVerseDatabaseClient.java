package migration.core.db.multivalue.impl.uv;

import java.text.MessageFormat;

import asjava.uniclientlibs.UniDynArray;
import asjava.uniclientlibs.UniException;
import asjava.uniclientlibs.UniTokens;
import asjava.uniobjects.UniJava;
import asjava.uniobjects.UniSession;
import asjava.uniobjects.UniSessionException;
import asjava.uniobjects.UniSubroutine;
import migration.core.db.multivalue.IMVDatabaseClient;
import migration.core.db.multivalue.MVProviderException;
import migration.core.model.mv.MVFile;

public class UniVerseDatabaseClient implements IMVDatabaseClient {

	private static final String XTOOLSUB = "*XTOOLSUB";

	private String m_host;
	private int m_port;
	private String m_account;
	private String m_username;
	private String m_password;
	
	private UniJava m_uniJava;

	public UniVerseDatabaseClient(String host, int port, String account, String username, String password) {
		m_host = host;
		m_port = port;
		m_account = account;
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
			session.setAccountPath(m_account);
			session.setUserName(m_username);
			session.setPassword(m_password);
			session.connect();
			invokeXtoolsub(session, XTOOLSUBConstant.Initialize, "");
			invokeXtoolsub(session, XTOOLSUBConstant.LogtoAccount, m_account);
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
	
//	private String constructMkdirCommand(String account) {
//		return MessageFormat.format("DOS /C mkdir {0}", account);
//	}
//	
//	private String constructMakeAccountCommand(String accountPath, String u2Home) {
//		return MessageFormat.format("DOS /C cd {0} & {1}\\bin\\mkaccount.exe NEWACC", accountPath, u2Home);
//	}
	
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
			createAccount(accountName, session);
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

//	private void createAccount1(String accountName, UniSession session) throws UniException {
//		String u2Home = getU2Home(session);
//		String accountPath = u2Home + "\\" + accountName;
//		UniCommand command = session.command(constructMkdirCommand(accountPath));
//		command.exec();
//		System.out.println(command.status() + ": " + command.response());
//		command = session.command(constructMakeAccountCommand(accountPath, u2Home));
//		command.exec();
//		System.out.println(command.status() + ": " + command.response());
//	}
	
	private void createAccount(String accountName, UniSession session) throws UniException {
		UniDynArray params = new UniDynArray(session, "");
		params.replace(1, getU2Home(session) + "\\" + accountName);
		params.replace(2, accountName);
		params.replace(3, "1");
		UniDynArray flavor = new UniDynArray(session, "");
		flavor.replace(2, "NEWACC");
		flavor.replace(3, "0");
		params.replace(4, flavor);
		invokeXtoolsub(session, XTOOLSUBConstant.NewAcct, params.toString());
	}

	@Override
	public void createFile(MVFile fileModel) throws MVProviderException {
		// TODO Auto-generated method stub

	}

}
