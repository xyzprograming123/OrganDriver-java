

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

public class TransplantGraph implements Serializable{
	public static final long serialVersionUID = 1L;
	private ArrayList<Patient> donors = new ArrayList<Patient>();
	private ArrayList<Patient> recipients = new ArrayList<Patient>();
	public static final int MAX_PATIENTS = 100;

	private boolean[][] connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
	private boolean[][] emptyConnections = new boolean[MAX_PATIENTS][MAX_PATIENTS]; // to reset the maxtrix

	public TransplantGraph() {

	}
	
	

	public ArrayList<Patient> getDonors() {
		return donors;
	}



	public void setDonors(ArrayList<Patient> donors) {
		this.donors = donors;
	}



	public ArrayList<Patient> getRecipients() {
		return recipients;
	}



	public void setRecipients(ArrayList<Patient> recipients) {
		this.recipients = recipients;
	}



	public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws IOException {
		TransplantGraph tempGraph = new TransplantGraph();
		String valuesFromFile[] = new String[5];
		Patient tempPatient;
		BloodType tempBloodType;

		try { // Importing DONOR File
				// from CSE 214 HW website
			FileInputStream fis = new FileInputStream(donorFile);
			InputStreamReader instream = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(instream);
			System.out.println("Loading data from 'donors.txt'...");
			String temp = "";
			String data = "";

			while (((temp = reader.readLine()) != null)) { // checks if there is a next line or not
				data = temp;
				valuesFromFile = data.split(",");
				removeSpaces(valuesFromFile);

				int ID = Integer.parseInt(valuesFromFile[0]);// The first element in the line is the ID
				int age = Integer.parseInt(valuesFromFile[2]);// The third element in the line is the age

				tempBloodType = new BloodType(valuesFromFile[4]); // Creates a temp bloodType
				tempPatient = new Patient(ID, valuesFromFile[1], age, valuesFromFile[3], tempBloodType);
				tempPatient.setDonor(true); // sets if the patient is a donor or not. In this case, the patient is a
											// donor
				tempGraph.addDonor(tempPatient);
			}

			reader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("DONOR file does not exist");
		}

		try { // Imports the recipientFile
				// Reader code from CSE 214 HW website
			FileInputStream fis = new FileInputStream(recipientFile);
			InputStreamReader instream = new InputStreamReader(fis);
			System.out.println("Loading data from 'recipients.txt'...");
			BufferedReader reader = new BufferedReader(instream);
			String temp = "";
			String data = "";
			while (((temp = reader.readLine()) != null)) { // checks if there is a next line or not
				data = temp;
				valuesFromFile = data.split(",");
				removeSpaces(valuesFromFile);

				int ID = Integer.parseInt(valuesFromFile[0]);// The first element in the line is the ID
				int age = Integer.parseInt(valuesFromFile[2]);// The third element in the line is the age

				tempBloodType = new BloodType(valuesFromFile[4]);
				tempPatient = new Patient(ID, valuesFromFile[1], age, valuesFromFile[3], tempBloodType);
				tempPatient.setDonor(false);// sets if the patient is a donor or not. In this case, the patient is a
											// recipient
				tempGraph.addRecipient(tempPatient);
			}

			reader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("RECIPIENT file does not exist");
		}

		return tempGraph; // Returns the transplantGraph object

	}

	public void addRecipient(Patient patient) {
		patient.setDonor(false);
		recipients.add(patient);
		updateConnections();
	}

	public void addDonor(Patient patient) {
		patient.setDonor(true);
		donors.add(patient);
		updateConnections();
	}

	public void removeRecipient(String name) {
		int arrayNum = 0; // stores the index that contains the name
		boolean exists = false;

		for (int i = 0; i < recipients.size(); i++) {
			if (recipients.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
				arrayNum = i;
				exists = true;
			}
		}

		if (exists) {
			System.out.println("\n" + recipients.get(arrayNum).getName() + " was removed from the recipients list.");
			recipients.remove(arrayNum);
			for(int i = arrayNum; i< recipients.size();i++)
				recipients.get(i).setID(i);// resets IDs 
			updateConnections();
		} else {
			System.out.println("This person is not in the recipients list");
		}

	}

	public void removeDonor(String name) {
		int arrayNum = 0; // stores the index that contains the name
		boolean exists = false;

		for (int i = 0; i < donors.size(); i++) {
			if (donors.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
				arrayNum = i;
				exists = true;
			}
		}

		if (exists) {
			System.out.println("\n" + donors.get(arrayNum).getName() + " was removed from the organ donor list.");
			
			donors.remove(arrayNum);
			for(int i = arrayNum; i< donors.size();i++)
				donors.get(i).setID(i);// resets IDs 
			
			updateConnections();
		} else {
			System.out.println("This person is not in the donors list");
		}

	}

	public void printAllRecipients() {
		int connections = 0;
		String temp = ""; // Use to store the toString from patient
		boolean moreThanOne; // use to add commas
		System.out.println("Index | Recipient Name     | Age | Organ Needed  | Blood Type | Donor ID\r\n"
				+ "========================================================================");
		for (int i = 0; i < recipients.size(); i++) {
			temp = recipients.get(i).toString();
			connections = 0;
			moreThanOne = false;// use to add commas
			recipients.get(i).setNumOfConnections(connections);//resets the number of connections 
			for (int j = 0; j < donors.size(); j++) {

				if (this.connections[donors.get(j).getID()][recipients.get(i).getID()]) {
					if (moreThanOne)
						temp += ",";
					
					temp += String.format("%2d", donors.get(j).getID());
					connections++;
					recipients.get(i).setNumOfConnections(connections);
					
					moreThanOne = true;// add commas
				}
			}
			System.out.println(temp);
		}

	}

	public void printAllDonors() {
		boolean moreThanOne = false;
		int connections = 0;
		String temp = ""; // Use to store the toString from patient
		System.out.println("Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient ID\r\n"
				+ "============================================================================");
		for (int i = 0; i < donors.size(); i++) {
			temp = donors.get(i).toString();
			connections = 0;
			moreThanOne = false;// use to add commas
			donors.get(i).setNumOfConnections(connections);
			for (int j = 0; j < recipients.size(); j++) {

				if (this.connections[donors.get(i).getID()][recipients.get(j).getID()]) {
					if (moreThanOne)
						temp += ",";
					temp += String.format("%2d", recipients.get(j).getID());
					connections++;
					donors.get(i).setNumOfConnections(connections);
					moreThanOne = true;// add commas
				}
			}
			System.out.println(temp);
		}

	}

	public void updateConnections() {
		this.connections = this.emptyConnections; // resets the matrix
		boolean compatibleBloodtype;

		for (int i = 0; i < donors.size(); i++) {
			for (int j = 0; j < recipients.size(); j++) {
				compatibleBloodtype = BloodType.isCompatible(recipients.get(j).getBloodType(),
						donors.get(i).getBloodType());
				this.connections[i][j] = false;
				if (compatibleBloodtype) {// checks if the organ matches
					if (donors.get(i).getOrgan().toLowerCase().equals(recipients.get(j).getOrgan().toLowerCase())) {
						// checks if the organs match. Case insensitive
						this.connections[i][j] = true;
						
					}
				}

			}

		}

	}
	
	public void printConnections() {
		for (int i = 0; i < donors.size(); i++) {
			for (int j = 0; j < recipients.size(); j++) {
				System.out.print(this.connections[i][j] + "\t");
			}

			System.out.println();
		}

	}

	public static void removeSpaces(String[] temp) {

		for (int i = 1; i < temp.length; i++) {
			temp[i] = temp[i].replaceFirst("\\s", "");
		}

	}
	
	public int totalAmountOfDonors() {
		return this.donors.size();
		
	}
	
	public int totalAmountOfRecipients() {
		return this.recipients.size();
		
	}

}
