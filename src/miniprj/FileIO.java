package miniprj;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/** File Input/Output Methods
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class FileIO {
	/**
	 * Save to file given a file path
	 * @param arr List to be stored
	 * @param dir File path
	 */
	public static void saveToFile(ArrayList arr, String dir) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(dir);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(arr);
			oos.close();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Read from file given a file path
	 * @param dir File path
	 * @return Returns list of data from file
	 */
	public static ArrayList readFile(String dir) {
        try
        {
            FileInputStream fis = new FileInputStream(dir);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList temp = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            
            return temp;
         }catch(IOException ioe){
             //ioe.printStackTrace();
             return new ArrayList();
          }catch(ClassNotFoundException c){
             System.out.println("Class not found");
             //c.printStackTrace();
             return new ArrayList();
          }
	}
}
