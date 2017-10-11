/**
 * 
 */
package model;

import java.util.List;

/**
 * @author C.B
 *
 */
public interface TransactionAvaibleListener {
	
	public void transactionAvaible(List<OCRTransaction> details);

}
