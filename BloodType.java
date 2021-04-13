
import java.io.Serializable;

public class BloodType implements Serializable{
	public static final long serialVersionUID = 1L;
	private String bloodType;
	
	public BloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	
	public static boolean isCompatible(BloodType recipient,BloodType donor) {
		if(recipient.getBloodType().toUpperCase().equals("AB")) {// recipient AB can recieve any blood type
			return true;
		}else if(donor.getBloodType().toUpperCase().equals("O")) {// donor O is always compatible
			return true;
		}else if(recipient.getBloodType().toUpperCase().equals(donor.getBloodType().toUpperCase())) { //The bloodtypes are the same
			return true;
		} 
		
		return false; //Everything else is false
		
	}
	
	public String toString() {
		return this.bloodType;
	}
	
	
}
