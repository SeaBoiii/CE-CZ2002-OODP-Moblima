package miniprj;

import java.io.Serializable;

/** Represents an administrator
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Admin implements Serializable{
	private String username;
	private String password;
	
	/** Creates an admin with the specified username and password
	 * @param username Admin login username
	 * @param password Admin login password
	*/
	public Admin(String username, String password) {	//constructor	
		this.username = username;			
		this.password = password;
	}
	
	/** Get admin username
	 * @return A string representing the admin's username
	*/
	public String getUsername() { //method 			
		return username;
	}
	
	/** Get admin password
	 * @return A string representing the admin's password
	*/
	public String getPassword() { //method
		return password;
	}
	
	/** Set admin username
	 * @param user A string representing the admin's new username
	*/
	public void setUsername(String user) {
		this.username = user;
	}
	
	/** Set admin password
	 * @param pass A string representing the admin's new password
	*/
	public void setPassword(String pass) {
		this.password = pass;
	}
}