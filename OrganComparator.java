

import java.io.Serializable;
import java.util.Comparator;

public class OrganComparator implements Comparator<Patient> ,Serializable{
	
	public static final long serialVersionUID = 1L;

	public int compare(Patient o1,Patient o2) {
		Patient p1 = (Patient)o1;
		Patient p2 = (Patient)o2;
		return (p1.getOrgan().compareToIgnoreCase(p2.getOrgan()));
	}
}
