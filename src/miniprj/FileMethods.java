package miniprj;
/** File IO Interface 
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public interface FileMethods {
	/**Reload local data by reading from file
	 * 
	 */
	abstract void reloadData();
	/**
	 * Save data to file and reload local data from file
	 */
	abstract void saveAndReloadData();
}
