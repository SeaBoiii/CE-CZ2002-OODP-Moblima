package miniprj;

import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/** Helper class for various function
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/** Helper class with helper methods
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class CommandUI {

	/**
	 * Default Constructor
	 */
	public CommandUI() {

	}

	/**
	 * Print a box base on length of text to be place inside
	 * @param text Text to be printed inside the box
	 * @param offset Padding for the text
	 * @param height Height of the box
	 */
	public static void printBox(String text, int offset, int height) {

		int length = text.length();
		int textInsert = (int) Math.ceil((height-2)/2);
		System.out.println();
		System.out.println();
		System.out.print("+");
		printDottedLine(2*offset+length);
		System.out.println("+");

			for(int k=0;k<(height-2);k++) {

				System.out.print("|");
				printWhiteSpace(offset);

				if(k==textInsert) {
					System.out.print(text);
				}else {
					printWhiteSpace(text.length());
				}

				printWhiteSpace(offset);
				System.out.print("|");
				System.out.println();

			}

		System.out.print("+");
		printDottedLine(2*offset+length);
		System.out.println("+");
	}

	/**
	 * Print text and generate a line below
	 * @param text Text to be printed
	 * @param offset Padding for the text
	 */
	public static void printTitleWithLine(String text, int offset) {
		int length = text.length();
		System.out.print(text);
		printWhiteSpace(offset);
		System.out.println();
		printEqualsLine(offset);

	}

	/**
	 * Rounding double value to certain precision
	 * @param value Value to be rounded
	 * @param precision Rounding precision
	 * @return Return rounded value
	 */
	public static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}

	/**
	 * Print white space on console
	 * @param num Number of whitespaces
	 */
	private static void printWhiteSpace(int num) {
		for(int i=0;i<num;i++) {
				System.out.print(" ");
		}
	}
	/**
	 * Print dotted line on console
	 * @param num Length of line
	 */
	private static void printDottedLine(int num) {
		for(int i=0;i<num;i++) {
				System.out.print("-");
		}
	}
	/**
	 * Print line with equal sign
	 * @param num Length of line
	 */
	private static void printEqualsLine(int num) {
		for(int i=0;i<num;i++) {
				System.out.print("=");
		}
	}

	/**
	 * Get integer input from users with range limits
	 * @param min Minimum value allowed
	 * @param max Maximum value allowed
	 * @return Return the integer value entered
	 */
	public static int getNumberInput(int min,int max) {
		Scanner sc = new Scanner(System.in);
		int result=0;


		while(true) {

			try {
				result = sc.nextInt();
				if(result <= max && result >= min) {
					break;
				}else {
					throw new NoSuchElementException("demo");
				}
			}catch(InputMismatchException ie) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number between "+min+"-"+max+": ");
				sc.next();
				continue;
			}catch(NoSuchElementException e) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number between "+min+"-"+max+": ");
				continue;
			}catch(IllegalStateException se) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number between "+min+"-"+max+": ");
				continue;
			}
		}
		return result;

	}

	/**
	 * Get integer input with range limits
	 * @return Return the integer value entered
	 */
	public static int getNumberInput() {
		Scanner sc = new Scanner(System.in);
		int result=0;

		while(true) {
			try {
				result = sc.nextInt();
				break;
			}catch(InputMismatchException ie) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number: ");
				sc.next();
				continue;
			}catch(NoSuchElementException e) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number: ");
				continue;
			}catch(IllegalStateException se) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number: ");
				continue;
			}
		}
		return result;

	}

	/**
	 * Get double input with range limits
	 * @return Return the double value entered
	 */
	public static double getDoubleInput() {
		Scanner sc = new Scanner(System.in);
		double result=0;

		while(true) {

			try {
				result = sc.nextDouble();
				break;
			}catch(InputMismatchException ie) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number: ");
				sc.next();
				continue;
			}catch(NoSuchElementException e) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number: ");
				continue;
			}catch(IllegalStateException se) {
				System.out.println("\nInvalid input");
				System.out.print("Please enter a number: ");
				continue;
			}
		}
		return result;

	}

	//return true if contact number format is valid
	/**
	 * Check if contact number is valid
	 * @param s Contact number to check
	 * @return Return TRUE if contact number is valid, otherwise FALSE
	 */
	public static boolean isValid(String s)
	{
	        // The given argument to compile() method
	        // is regular expression. With the help of
	        // regular expression we can validate mobile
	        // number.
	        // 1) Begins with 0 or 91
	        // 2) Then contains 7 or 8 or 9.
	        // 3) Then contains 9 digits
		Pattern p = Pattern.compile("(0/91)?[8-9][0-9]{7}");

	        // Pattern class contains matcher() method
	        // to find matching between given number
	        // and regular expression
	    Matcher m = p.matcher(s);
	    return (m.find() && m.group().equals(s));
	}

	// return contact number if valid, else error handling
	/**
	 * Check if contact number is valid if not ask user to type again
	 * @param s Contact number to check
	 * @return Return TRUE if contact number is valid, otherwise FALSE
	 */
	public static String phonenumchk(String s)
    {
		Scanner sc = new Scanner(System.in);
		do {
			if (isValid(s))
				return s;
			else {
				System.out.println("Invalid Number please re-enter");
				System.out.print("Mobile No : ");
				s = phonenumchk(sc.nextLine());

			}
		}while(true);
    }

	//return true if email format is valid
	/**
	 * Check valid email
	 * @param email Email to be checked
	 * @return Return TRUE if contact number is valid, otherwise FALSE 
	 */
	public static boolean isValidemail(String email) {
		   String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		   return email.matches(regex);
	}

	// return email if valid, else error handling
	/**
	 * Check if email is valid if not ask user to type again
	 * @param s Email to check
	 * @return Returns a email as a string
	 */
	public static String emailchk(String s)
    {
		Scanner sc = new Scanner(System.in);
		do {
			if (isValidemail(s))
				return s;
			else {
				System.out.println("Invalid e-mail please re-enter");
				System.out.print("e-mail : ");
				s = emailchk(sc.nextLine());
			}
		}while(true);
    }

	//return true if name formatting is valid
	/**
	 * Check if is string alphabetic  
	 * @param name String to check
	 * @return Return TRUE if string is alphabetic, otherwise FALSE 
	 */
	public static boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}

	// return name if valid, else error handling
	/**
	 * Check if is string alphabetic if not ask user enter again
	 * @param s String to check
	 * @return Return TRUE if string is alphabetic, otherwise FALSE 
	 */
	public static String namechk(String s)
    {
		Scanner sc = new Scanner(System.in);
		do {
			if (isAlpha(s))
				return s;
			else {
				System.out.println("Invalid name please re-enter");
				System.out.print("name : ");
				s = namechk(sc.nextLine());
			}
		}while(true);
    }

}
