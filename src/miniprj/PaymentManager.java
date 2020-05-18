package miniprj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/** Control class for payment related tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/

public class PaymentManager implements Serializable{
	
	/**
	 * Default constructor
	 */
	public PaymentManager() {

	}
	
	/**
	 * Create new payment
	 * @param paymentId Payment identification number
	 * @param price Amount to be paid
	 * @param paymentType Payment method
	 * @return Returns a payment entry
	 */
	public PaymentEntry createPayment(String paymentId, double price, PaymentType paymentType) {
		Date date = Calendar.getInstance().getTime();
		return new PaymentEntry(paymentId, price, date, paymentType);
	}
	
}
