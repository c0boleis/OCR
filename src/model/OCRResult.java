package model;

public class OCRResult {
	
	private char c;
	
	private long result;

	/**
	 * @param c
	 * @param result
	 */
	public OCRResult(char c, long result) {
		super();
		this.c = c;
		this.result = result;
	}

	/**
	 * @return the c
	 */
	public char getChar() {
		return c;
	}

	/**
	 * @return the result
	 */
	public long getResult() {
		return result;
	}

}
