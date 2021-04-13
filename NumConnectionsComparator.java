

import java.io.Serializable;
import java.util.Comparator;

public class NumConnectionsComparator implements Comparator<Object>,Serializable{
	
	public static final long serialVersionUID = 1L;
	
	public int compare(Object arg0, Object arg1) {
		Patient p1 = (Patient)arg0;
		Patient p2 = (Patient)arg1;
		
		if(p1.getNumOfConnections() == p2.getNumOfConnections())
			return 0;
		else if(p1.getNumOfConnections() > p2.getNumOfConnections())
			return 1;
		else 
			return -1;
		
	}

}

