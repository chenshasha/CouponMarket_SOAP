package module;


public class User {
	private String lname;
	private String fname;
	private String email;
	private String password;
	private String lastCheckin;
	private String curCheckin;
	
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastCheckin() {
		return lastCheckin;
	}
	public void setLastCheckin(String lastCheckin) {
		this.lastCheckin = lastCheckin;
	}
	public String getCurCheckin() {
		return curCheckin;
	}
	public void setCurCheckin(String curCheckin) {
		this.curCheckin = curCheckin;
	}

}
