package application;

public class DatabasesSettings {
	public static String mv_host;
	public static Integer  mv_port;
	public static String mv_account;
	
	DatabasesSettings(){
		mv_host=null;
		mv_port=null;
		mv_account=null;
	}
	
	public void set_MVHost(String host){
		mv_host=host;
	}

	public void set_MVPort(String port){
		mv_port= Integer.parseInt(port);
	}
	
	public void set_MVAccount(String account){
		mv_account= account;
	}
	
	public boolean allFieldsSpecified(){
		if (!mv_host.equals(null) && !mv_port.equals(null) && !mv_account.equals(null))
			return true;
		else 
			return false;
	}
}
