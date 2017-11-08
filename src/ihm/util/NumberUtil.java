package ihm.util;

public class NumberUtil {
	
	public static int getMax(int... tab) {
		int max = tab[0];
		for(int index = 0;index<tab.length;index++) {
			if(tab[index]>max) {
				max = tab[index];
			}
		}
		return max;
	}

}
