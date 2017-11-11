package model;

public class RGBSeuilFact {
	
	private ColorSeuilFact factRed;
	
	private ColorSeuilFact factGreen;
	
	private ColorSeuilFact factBlue;
	
	/**
	 * @param factRed
	 * @param factGreen
	 * @param factBlue
	 */
	public RGBSeuilFact(ColorSeuilFact factRed, ColorSeuilFact factGreen, ColorSeuilFact factBlue) {
		super();
		this.factRed = factRed;
		this.factGreen = factGreen;
		this.factBlue = factBlue;
	}

	public RGBSeuilFact() {
		this.factRed = new ColorSeuilFact(10,150);
		this.factGreen = new ColorSeuilFact(10,150);
		this.factBlue = new ColorSeuilFact(10,150);
	}

	/**
	 * @return the factRed
	 */
	public ColorSeuilFact getFactRed() {
		return factRed;
	}

	/**
	 * @return the factGreen
	 */
	public ColorSeuilFact getFactGreen() {
		return factGreen;
	}

	/**
	 * @return the factBlue
	 */
	public ColorSeuilFact getFactBlue() {
		return factBlue;
	}

}
