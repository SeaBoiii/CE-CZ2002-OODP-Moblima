package miniprj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/** Control class to manage admin related tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class AdminManager implements Serializable, FileMethods{			
	
	private static final long serialVersionUID = -8265719233518273959L;
	
	
	/**Stores all the admins usernames and passwords
	 */
	private ArrayList<Admin> adminList = new ArrayList<Admin>();
	private Scanner scans = new Scanner(System.in);
	
	
	/**Construct AdminManager object
	 * 
	 */
	public AdminManager() { //constructor
		adminList.addAll(FileIO.readFile(Directory.DIR_USERS));
		if(adminList.size()==0) {
		adminList.add(new Admin("root","password"));	//creating a new admin object from Admin.java
		}
		
	}
	
	/**Remove admin menu
	 * 
	 * @return Returns TRUE if successfully removed admin user or FALSE otherwise
	 */
	public boolean removeAdmin() {
		//System.out.println("Administrators:");
		if(adminList.size() == 1) {
			System.out.println("You can't remove the user as there's only one administrator!");
			return false;
		}
		CommandUI.printBox("Admin List", 5, 3);
		for(int x=0; x<adminList.size(); x++) {
			int y = x+1;
			System.out.println("[" + y + "] " + adminList.get(x).getUsername());
		}
		while (true) {
			try {
				System.out.println("Please enter the index to remove [-1 to return]: ");
				int username = scans.nextInt();
				if (username == -1)
					return false;
				else if (username >= adminList.size()+1 || username <= 0) {
					System.out.println("Please enter a valid index! ");
					continue;
				}
				adminList.remove(username-1);
				break;
			}
			catch (InputMismatchException e) {
				System.out.println("Please enter an index! ");
				scans.nextLine();
				continue;
			}
		}
		saveAndReloadData();

		return false;
	}
	
	/**Authenticate username and password
	 * 
	 * @param username Admin's username
	 * @param password Admin's password
	 * @return Returns TRUE if successfully verified admin user or FALSE otherwise
	 */
	public boolean authenticate(String username, String password) {
		for (int i=0; i<adminList.size(); i++) {
			//System.out.println(adminList.get(i).getUsername());
			//System.out.println(adminList.get(i).getPassword());
			if (adminList.get(i).getUsername().compareTo(username) == 0 && 
					adminList.get(i).getPassword().compareTo(password) == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**Add a new admin user into database
	 * 
	 * @param username Username to be added in
	 * @param password Password to be added in
	 */
	public void addAdmin(String username, String password) {
		adminList.add(new Admin(username, password));
		FileIO.saveToFile(adminList, Directory.DIR_USERS);
		reloadData();
	}
	
	
	/**Open a prompt to update admin details
	 * 
	 */
	public void updateAdmin() {	//use it to update password
		CommandUI.printBox("Admin List", 5, 3);
		int username;
		//System.out.println("Administrators:");
		for(int x=0; x<adminList.size(); x++) {
			int y = x+1;
			System.out.println("[" + y + "] " + adminList.get(x).getUsername());
		}
		while (true) {
			try {
				System.out.println("Enter administrator index to update [-1 to return]: ");
				username = scans.nextInt();
				if (username == -1)
					return;
				else if (username >= adminList.size()+1 || username <= 0) {
					System.out.println("Please enter a valid index! ");
					continue;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Please enter an index! ");
				scans.nextLine();
				continue;
			}
			scans.nextLine();
			break;
		}
		//String username = scans.next(); 
		//scans.nextLine();
		//now you will need to check for the admin username in the admin list.
		System.out.println("Please select which of the following to update: ");
		System.out.println("[1] Update Username");
		System.out.println("[2] Update Password");
		System.out.println("[3] Back");
		do {
			try {
				int userSelect = scans.nextInt();
				scans.nextLine();
				switch(userSelect) {
					case 1: 
						System.out.println("New Username: ");
						String newUser = scans.next();
						scans.nextLine();
						for(int x=0; x<adminList.size();x++) {
							if(adminList.get(x).getUsername().contentEquals(newUser)) {
								System.out.println("Administrator USERNAME exist!!!");
								return;
							}
						adminList.get(username-1).setUsername(newUser);
						System.out.println("User sucessfully updated!");
						saveAndReloadData();
						return;
						}
					case 2:
						System.out.println("New Password: ");
						String newPasswordA = scans.next();
						scans.nextLine();
						System.out.println("Re-enter Password: ");
						String newPasswordB = scans.next();
						scans.nextLine();
						if((newPasswordA.equals(newPasswordB))){
							adminList.get(username-1).setPassword(newPasswordA);
							saveAndReloadData();
							System.out.println("Password updated!");
							return;
						}else {
							System.out.println("Password miss-match, please try again!");
						}
						break;
					case 3:
						return;
					default:
						break; 
				}
			}
			catch (InputMismatchException e) {
				scans.nextLine();
				System.out.println("Error! Please try again...");
				continue;
			}
			}while(true);
	}
	
	/**Open a prompt to add a new admin into the list
	 * 
	 */
	public void adminSignup() {
		String passworda,passwordb = null;
		System.out.println("Please enter the new admin [-1 to return]: ");
		String username = scans.nextLine(); 
		if (username.equals("-1"))
			return;
		//check if the admin username exist
		for(int i=0; i<adminList.size();i++) {
			if(adminList.get(i).getUsername().contentEquals(username)) {
				System.out.println("Administrator USERNAME exist!!!");
				return;
			}
		}
		System.out.println("Please enter the password: ");
		passworda = scans.nextLine();
		System.out.println("Please re-enter the password: ");
		passwordb = scans.nextLine();
		if(passworda.equals(passwordb)) {
			addAdmin(username,passworda);
			saveAndReloadData();
			System.out.println("Admin is added successfully!");
			System.out.println("Returning you back to the main menu...");
		}
		else{
			System.out.println("Password mismatch, please retry!");
			return;
		}
		return;
		
	}

	@Override
	public void reloadData() {
		adminList.clear();
		adminList.addAll(FileIO.readFile(Directory.DIR_USERS));
		
	}
	
	@Override
	public void saveAndReloadData() {
		FileIO.saveToFile(adminList, Directory.DIR_USERS);
		reloadData();
		
	}
	
}
