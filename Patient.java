
import java.io.Serializable;
public class Patient implements Serializable{
	public static final long serialVersionUID = 1L;
	private String name;
	private String organ;
	private int age;
	private BloodType bloodType;
	private int ID; // 
	private boolean isDonor; // true if the patient is a donor
	private int numOfConnections;
	
	
	
	public Patient() {
		
	}
	
	public Patient(String name,int age,String organ,BloodType bloodType) {// For adding donor and recipeints
		
		this.name = name;
		this.age = age;
		this.organ = organ;
		this.bloodType = bloodType;
		
	}
	
	
	public Patient(int ID,String name,int age,String organ,BloodType bloodType) {
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.organ = organ;
		this.bloodType = bloodType;
		
	}
	
	

	public int getNumOfConnections() {
		return numOfConnections;
	}

	public void setNumOfConnections(int numOfConnections) {
		this.numOfConnections = numOfConnections;
	}

	public BloodType getBloodType() {
		return bloodType;
	}


	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}


	public boolean isDonor() {
		return isDonor;
	}


	public void setDonor(boolean isDonor) {
		this.isDonor = isDonor;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOrgan() {
		return organ;
	}


	public void setOrgan(String organ) {
		this.organ = organ;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}
	
	
	public String toString() {
		String temp = "";
		temp = String.format("%4d %2s %-15s %4s %2d %2s %-13s %-5s %-3s %4s",this.ID,"|",this.name,"|",this.age,"|",this.organ,"|"
				, this.bloodType.getBloodType() , "|");
		return temp;
		
	}
	
	
	
	
	
}
