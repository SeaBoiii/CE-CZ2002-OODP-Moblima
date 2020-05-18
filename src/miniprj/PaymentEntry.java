package miniprj;

import java.io.Serializable;
import java.util.Date;
/** Represents payment details
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class PaymentEntry implements Serializable{
	
	/**
	 * Payment Identification Number
	 */
	private String paymentId;
	/**
	 * Total amount paid
	 */
	private double price;
	/**
	 * Date of payment
	 */
	private Date date;
	/**
	 * Payment method (VISA,MASTERCARD etc.)
	 */
	private PaymentType paymentType;
	
	/**
	 * Construct payment entry
	 * @param paymentId Payment Identification number
	 * @param price Total amount to be paid
	 * @param date Date of payment
	 * @param paymentType Payment method
	 */
	public PaymentEntry(String paymentId, double price, Date date, PaymentType paymentType ) {
		this.paymentId = paymentId;
		this.price = price;
		this.date = date;
		this.paymentType = paymentType;
	}
	/**
	 * Get payment identification number
	 * @return Returns payment identification number
	 */
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * Get total amount paid
	 * @return Returns total amount paid as a double value
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Set new payment amount
	 * @param price New payment amount
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * Get date of payment
	 * @return Returns date of payment
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set date of payment
	 * @param date New date of payment
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * Get payment method
	 * @return Returns payment method as a PaymentType enum value
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}
	/**
	 * Set payment type
	 * @param paymentType New payment type 
	 */
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
		
		
}