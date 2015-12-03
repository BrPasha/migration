package application;

public class DatabasesSettings {
	private String rHost = "wal-vm-sql2mv";//"che-l-im01"; //"wal-vm-sql2mv";
	private String rDBname = "sakila"; 
	private Integer rPort = 3306;
	private String rUser  = "root"; //"mysql"; //"root";
	private String rPsw = "admin"; //"mysql"; //"admin";
	
	
	private String mvHost = "localhost";
	private Integer mvPort = 31438;
	private String mvAccount = rDBname;
	private String mvUser = "u2user";
	private String mvPsw = "!u2user";
	
	public void setMVHost(String host){
		this.mvHost=host;
	}
	
	public String getMVHost(){
		return mvHost;
	}

	public void setMVPort(Integer port){
		this.mvPort= port;
	}
	
	public Integer getMVPort(){
		return mvPort;
	}
	
	public void setMVAccount(String account){
		this.mvAccount= account;
	}
	
	public String getMVAccount(){
		return mvAccount;
	}
	
	public void setMVUser(String user){
		this.mvUser = user;
	}
	
	public String getMVUser(){
		return mvUser;
	}
	
	public void setMVPsw(String psw){
		this.mvPsw = psw;
	}
	
	public String getMVPsw(){
		return mvPsw;
	}

	public String getRHost() {
		return rHost;
	}

	public void setRHost(String rHost) {
		this.rHost = rHost;
	}

	public Integer getRPort() {
		return rPort;
	}

	public void setRPort(Integer rPort) {
		this.rPort = rPort;
	}

	public String getRUser() {
		return rUser;
	}

	public void setRUser(String rUser) {
		this.rUser = rUser;
	}

	public String getRPsw() {
		return rPsw;
	}

	public void setRPsw(String rPsw) {
		this.rPsw = rPsw;
	} 
	
	public String getRDBName() {
		return rDBname;
	}

	public void setRDBName(String rDBname) {
		this.rDBname = rDBname;
	} 
}
