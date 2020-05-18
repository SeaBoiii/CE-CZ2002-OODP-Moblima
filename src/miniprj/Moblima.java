package miniprj;

import java.util.InputMismatchException;
import java.util.Scanner;

/** Main menu (This is the first menu the user sees)
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Moblima {

	public static void main(String[] args) {

		

		AdminApp app = new AdminApp();
		CustomerApp c_app = new CustomerApp();
		
		
		Scanner sc = new Scanner(System.in);
		
		Manager.pm.reloadData();
		int index;
		
		do {
			try {
				CommandUI.printBox("Welcome to Moblima", 5, 7);
				System.out.println();
				System.out.println("[1] Customer");
				System.out.println("[2] Admin");
				System.out.println("[3] Exit");
				
				System.out.print("\nPlease select an option: ");
				index = sc.nextInt();
				
				switch(index) {
					case 1:
						c_app.open();
						break;
					case 2:
						app.loginPrint();
						break;
					case 3:
						System.out.println("Exiting..app.");
						sc.close();
						return;
					default:
						System.out.println("Please enter a valid option!");
						break;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter a valid option!");
				continue;
			}
		}while(true);

	}

}
