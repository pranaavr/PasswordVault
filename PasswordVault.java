import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * CMSC 413 Intro to Cyber Security Project 1
 * @author Jorge Zaquitzal
 * @author Pranaav Rao
 */
public class PasswordVault {
//comment 12/1 4:53
//boobs
	public static void main(String[] args) throws IOException {

		String website;
		String userName = "";
		String password = "";
		String choice;
		boolean loop = true;

		int count;

		Scanner inputObject = new Scanner(System.in);

		// Prompt
		System.out.println("Welcome to the password Valut!");
		System.out.println();

		// create a the file object called password
		File file = new File("SecureFile.txt");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		// created filewriter objected called fw
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		// created a printwriter object called pw
		PrintWriter pw = new PrintWriter(fw);


		while (loop) {

			System.out.println("Please enter Website");
			website = inputObject.nextLine();
			System.out.println("Please enter username");
			userName = inputObject.nextLine();
			System.out.println("Please enter password");
			password = inputObject.nextLine();

			pw.printf("%14s %20s %21s\n", website, userName, password);

			System.out.println("Data has been saved to file.");
			System.out.println();
			System.out.println("Would you like to enter more information ? Yes or no");
			choice = inputObject.nextLine();
			if (choice.equals("yes")) {
				loop = true;
			} else {
				loop = false;
			}

		}
		pw.close();
	}

}
