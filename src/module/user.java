package module;

import java.sql.Date;

public class user {
	private String lname;
	private String fname;
	private String email;
	private String password;
	private Date lastCheckin;
	private Date curCheckin;
	
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
	public Date getLastCheckin() {
		return lastCheckin;
	}
	public void setLastCheckin(Date lastCheckin) {
		this.lastCheckin = lastCheckin;
	}
	public Date getCurCheckin() {
		return curCheckin;
	}
	public void setCurCheckin(Date curCheckin) {
		this.curCheckin = curCheckin;
	}

}
