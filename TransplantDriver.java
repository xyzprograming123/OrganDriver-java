
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TransplantDriver implements Serializable {
	public static final long serialVersionUID = 1L;

	public static final String DONOR_FILE = "donors.txt";
	public static final String RECIPIENT_FILE = "recipients.txt";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Scanner stdin = new Scanner(System.in);
		boolean continuing = true;
		boolean subMenu = false;

		String option = "";

		boolean isLoaded = false;
		TransplantGraph transplantGraph = new TransplantGraph();
		try { // From CSE 214 homework page
			File f = new File("transplant.obj");
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			transplantGraph = (TransplantGraph) ois.readObject();
			System.out.println("Loading data from transplant.obj..");
			ois.close();
			isLoaded = true;
		} catch (FileNotFoundException ex) {
			System.out.println("transplant.obj not found. Creating new TransplantGraph object...");
			// transplantGraph.obj is created by option Q;

		}

		if (!isLoaded) {// runs when no save file is found
			transplantGraph = TransplantGraph.buildFromFiles(DONOR_FILE, RECIPIENT_FILE);
		}

		transplantGraph.updateConnections();

		while (continuing) {

			printMenu();
			System.out.print("Please select an option:");
			option = stdin.nextLine().toUpperCase();

			switch (option) {
			case "LR":
				transplantGraph.printAllRecipients();
				break;
			case "LO":
				transplantGraph.printAllDonors();
				break;
			case "AO":
				int age = 0;
				System.out.print("Please enter the organ donor name:");
				String donorName = stdin.nextLine();
				System.out.print("Please enter the organs " + donorName + " is donating:");
				String organName = stdin.nextLine();
				System.out.print("Please enter the blood type of " + donorName + ":");
				String bloodType = stdin.nextLine();
				BloodType tempBloodType = new BloodType(bloodType);
				try {
					System.out.print("Please enter the age of " + donorName + ":");
					age = Integer.parseInt(stdin.nextLine());
				} catch (NumberFormatException ex) {
					System.out.println("\n" + "Please Enter a valid age!");
					continue;
				}
				Patient tempPatient = new Patient(donorName, age, organName, tempBloodType);
				tempPatient.setID(transplantGraph.totalAmountOfDonors());
				transplantGraph.addDonor(tempPatient);

				System.out.println("\n"+"The organ donor with ID " + tempPatient.getID()
						+ " was successfully added to the donor list!");

				break;
			case "AR":
				System.out.print("Please enter new recipient's name:");
				String recipientName = stdin.nextLine();
				System.out.print("Please enter the recipient's blood type:");
				bloodType = stdin.nextLine();
				tempBloodType = new BloodType(bloodType);
				try {// Catches numberFormatException
					System.out.print("Please enter the recipient's age:");
					age = Integer.parseInt(stdin.nextLine());
				} catch (NumberFormatException ex) {
					System.out.println("\n" + "Please Enter a valid age!");
					continue;
				}
				System.out.print("Please enter the organ needed:");
				organName = stdin.nextLine();
				tempPatient = new Patient(recipientName, age, organName, tempBloodType);
				tempPatient.setID(transplantGraph.totalAmountOfRecipients());
				transplantGraph.addRecipient(tempPatient);

				System.out.println("\n"+recipientName + " is now on the organ transplant waitlist!");

				break;
			case "RO":
				System.out.print("Please enter the name of the organ donor to remove: ");
				String removeName = stdin.nextLine();

				transplantGraph.removeDonor(removeName);

				break;
			case "RR":
				System.out.print("Please enter the name of the recipient to remove: ");
				removeName = stdin.nextLine();

				transplantGraph.removeRecipient(removeName);
				break;
			case "SR":
				subMenu = true;
				TransplantGraph cloneTransplantGraph = new TransplantGraph();

				for (int i = 0; i < transplantGraph.totalAmountOfRecipients(); i++)// clones the object
					cloneTransplantGraph.addRecipient((Patient) transplantGraph.getRecipients().get(i));
				for (int i = 0; i < transplantGraph.totalAmountOfDonors(); i++)
					cloneTransplantGraph.addDonor(transplantGraph.getDonors().get(i));

				while (subMenu) {
					printSubMenuRecipient();
					System.out.println("Please select an option:");
					String subOption = stdin.nextLine().toUpperCase();

					switch (subOption) {
					case "I":
						transplantGraph.printAllRecipients(); // returns the original list
						break;
					case "N":
						Collections.sort(cloneTransplantGraph.getRecipients(), new NumConnectionsComparator());
						cloneTransplantGraph.printAllRecipients();
						break;
					case "B":
						Collections.sort(cloneTransplantGraph.getRecipients(), new BloodTypeComparator());
						cloneTransplantGraph.printAllRecipients();
						break;
					case "O":
						ArrayList<Patient> clone = cloneTransplantGraph.getRecipients();
						Collections.sort(clone, new OrganComparator());
						cloneTransplantGraph.printAllRecipients();
						break;
					case "Q":
						subMenu = false;
						System.out.println("Returning to main menu.");
						break;
					default:
						System.out.println("Please enter a valid option:");
					}

				}

				break;
			case "SO":
				subMenu = true;
				cloneTransplantGraph = new TransplantGraph();
				for (int i = 0; i < transplantGraph.totalAmountOfRecipients(); i++)// clones the object
					cloneTransplantGraph.addRecipient((Patient) transplantGraph.getRecipients().get(i));
				for (int i = 0; i < transplantGraph.totalAmountOfDonors(); i++)
					cloneTransplantGraph.addDonor(transplantGraph.getDonors().get(i));

				while (subMenu) {
					printSubMenuDonor();
					System.out.println("Please select an option:");
					String subOption = stdin.nextLine().toUpperCase();

					switch (subOption) {
					case "I":
						transplantGraph.printAllDonors();

						break;
					case "N":
						Collections.sort(cloneTransplantGraph.getDonors(), new NumConnectionsComparator());
						cloneTransplantGraph.printAllDonors();
						break;
					case "B":
						Collections.sort(cloneTransplantGraph.getDonors(), new BloodTypeComparator());
						cloneTransplantGraph.printAllDonors();
						break;
					case "O":
						ArrayList<Patient> clone = cloneTransplantGraph.getDonors();
						Collections.sort(clone, new OrganComparator());
						cloneTransplantGraph.printAllDonors();
						break;
					case "Q":
						subMenu = false;
						System.out.println("Returning to main menu.");
						break;
					default:
						System.out.println("Please enter a valid option:");
					}

				}
				break;
			case "Q":
				continuing = false;
				System.out.println("Writing data to transplant.obj...");
				System.out.println("Program Termanting Normally...");
				File f = new File("transplant.obj");
				FileOutputStream fos = new FileOutputStream(f);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(transplantGraph);
				// save the this object file

				break;

			default:
				System.out.println("Please enter a valid option from the menu!");
			}

		}

		stdin.close();

	}

	public static void printMenu() {
		System.out.println("\n" + "Menu:\r\n" + "    (LR) - List all recipients\r\n" + "    (LO) - List all donors\r\n"
				+ "    (AO) - Add new donor\r\n" + "    (AR) - Add new recipient\r\n" + "    (RO) - Remove donor\r\n"
				+ "    (RR) - Remove recipient\r\n" + "    (SR) - Sort recipients\r\n" + "    (SO) - Sort donors\r\n"
				+ "    (Q) - Quit" + "\n");

	}

	public static void printSubMenuRecipient() {
		System.out.println("\n" + "    (I) Sort by ID\r\n" + "    (N) Sort by Number of Donors\r\n"
				+ "    (B) Sort by Blood Type\r\n" + "    (O) Sort by Organ Needed\r\n" + "    (Q) Back to Main Menu");
	}

	public static void printSubMenuDonor() {
		System.out.println("\n" + "    (I) Sort by ID\r\n" + "    (N) Sort by Number of Donors\r\n"
				+ "    (B) Sort by Blood Type\r\n" + "    (O) Sort by Organ Donated\r\n" + "    (Q) Back to Main Menu");
	}

}
