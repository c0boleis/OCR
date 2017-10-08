package ihm.actions;

import java.awt.Point;

public class PointGomme extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2802826859176504271L;
	
	private int taille = 1;
	

	/**
	 * @param x
	 * @param y
	 */
	public PointGomme(int x, int y,int t) {
		super(x, y);
		this.taille = t;
	}

	/**
	 * @param p
	 */
	public PointGomme(Point p,int t) {
		super(p);
		this.taille = t;
	}

	/**
	 * @return the taille
	 */
	public int getTaille() {
		return taille;
	}
	
	private void process() {
		
	}

}
