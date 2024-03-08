package model;

public class ServerConfiguration {
	private int dB_Port;
	private String ip_Direction;
	private String dB_Name;
	private String userName;
	private String password;
	private int clientPort;
	
	public ServerConfiguration() {
		
	}

	public int getdB_Port() {
		return dB_Port;
	}

	public void setdB_Port(int dB_Port) {
		this.dB_Port = dB_Port;
	}

	public String getIp_Direction() {
		return ip_Direction;
	}

	public void setIp_Direction(String ip_Direction) {
		this.ip_Direction = ip_Direction;
	}

	public String getdB_Name() {
		return dB_Name;
	}

	public void setdB_Name(String dB_Name) {
		this.dB_Name = dB_Name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	@Override
	public String toString() {
		return "ServerConfiguration [dB_Port=" + dB_Port + ", ip_Direction="
				+ ip_Direction + ", dB_Name=" + dB_Name + ", userName="
				+ userName + ", password=" + password + ", clientPort="
				+ clientPort + "]";
	}
}
