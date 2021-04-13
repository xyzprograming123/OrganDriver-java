

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient>,Serializable{
	
	public static final long serialVersionUID = 1L;
	
	public int compare(Patient o1,Patient o2) {
		Patient p1 = (Patient)o1;
		Patient p2 = (Patient)o2;
		
		ArrayList<String> bloodOrder = new ArrayList<String>();
		bloodOrder.add("A");
		bloodOrder.add("AB");
		bloodOrder.add("B");
		bloodOrder.add("O");// determines the order of the comparison
		
		int bloodOrderForP1 = bloodOrder.indexOf(p1.getBloodType().getBloodType());
		int bloodOrderForP2 = bloodOrder.indexOf(p2.getBloodType().getBloodType());
		
		if(bloodOrderForP1 == bloodOrderForP2)
			return 0;
		else if(bloodOrderForP1 > bloodOrderForP2)
			return 1;
		else 
			return -1;
		
	}
}
