package model;

public class OCRTransaction {
	
	private String title;
	
	private double price;
	
	public OCRTransaction(String title,double price) {
		this.title = title;
		this.price = price;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

}
