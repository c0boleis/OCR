package model;

public class ColorSeuilFact {
	
	private double valMax;
	
	private double valMin;

	/**
	 * @param valMax
	 * @param valMin
	 */
	public ColorSeuilFact(double valMin, double valMax) {
		super();
		this.valMax = valMax;
		this.valMin = valMin;
	}

	/**
	 * @return the valMax
	 */
	public double getValMax() {
		return valMax;
	}

	/**
	 * @param valMax the valMax to set
	 */
	public void setValMax(double valMax) {
		this.valMax = valMax;
	}

	/**
	 * @return the valMin
	 */
	public double getValMin() {
		return valMin;
	}

	/**
	 * @param valMin the valMin to set
	 */
	public void setValMin(double valMin) {
		this.valMin = valMin;
	}

}
