import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		String choice;
		boolean cont = true;
		String choice1;
		String choice2;
		Gui mygui = new Gui();
		
		mygui.mainFrame();

		/*
		 * Prompt to the user where the user can either 
		 * create a new password file or add to an 
		 * existing password file.
		 */
		System.out.println("Welcome to the password vault: \n");

		/*Outer loop that takes user 
		 *input until a valid selection has been made.
		 */
		while (cont) {

			System.out.println(
			"Press 1 to add existing password file.\n" 
			+ "Or press 2 to create a new password file.\n");
			choice = in.nextLine();

			if (choice.equals("1")) {
				/*
				 * If there is an existing file
				 * the user is asked what they would
				 * like to do with existing file.
				 * This decrypts the existing encrypted 
				 * file using the 8 digit key that was created.
				 */

				PasswordVault.decryptFile();

				/*
				 * Internal loop to prompt file options
				 */
				boolean loopChoice_2 = true;
				while (loopChoice_2) {

					System.out.println(
					"To view Username and passwords press 1\n" 
					+ "To delete entry press 2\n"
					+ "To Add to list press 3\n" 
					+ "To exit press 4\n");
					choice1 = in.next();

					/*
					 * Choice number 1 only prints
					 * the file contents.
					 */
					if (choice1.equals("1")) {

						System.out.println();
						PasswordVault.printFileContents();
						System.out.println();
						
						/*
						 * Choice number 2 deletes the
						 * record that the user wants to 
						 * remove from the file.
						 */
					} else if (choice1.equals("2")) {

						System.out.println(
						"Enter the website whos username and password\n" 
						+ "you'd like to delete:\n");
						String website = in.next();
						PasswordVault.deleteRecord(website);
						System.out.println("Account stating with " + website + " has been deleted.\n");

						/*
						 * Choice number 3 allows user
						 * add to the file.
						 */
					} else if (choice1.equals("3")) {


						/*
						 * Choice number 4 exits the
						 * program.
						 */
					} else if (choice1.equals("4")) {

						System.out.println("Exiting!");
						loopChoice_2 = false;

					}
				}
				/*
				 * If the program is exited then
				 * a full encryption of the file 
				 * is done.
				 */
				PasswordVault.encryptFile();
				File file = new File("password.txt");
				file.delete();
				cont = false;

			} else if (choice.equals("2")) {

				boolean loopChoice = true;

				while (loopChoice) {

					System.out.println(
					"To view Username and passwords press 1\n" 
					+ "To delete entry press 2\n"
					+ "To Add to list press 3\n" + "To exit press 4");
					choice2 = in.next();

					if (choice2.equals("1")) {

						System.out.println();
						PasswordVault.printFileContents();
						System.out.println();

					} else if (choice2.equals("2")) {

						System.out.println(
						"Enter the website whos username and password\n" 
						+ "you'd like to delete:\n");
						String website = in.next();
						PasswordVault.deleteRecord(website);
						System.out.println("Account stating with " + website + " has been deleted.\n");


					} else if (choice2.equals("4")) {

						System.out.println("Exiting!");
						loopChoice = false;

					}
				}
				
				PasswordVault.encryptFile();
				File file = new File("password.txt");
				file.delete();
				cont = false;
                
			} else if (choice.isEmpty()) {
					
				System.out.println("No entry was made.Please try again.\n");
				cont = true;
				
			}else {
			
			System.out.println(choice + " Is not an option. Please enter one of the two options.\n");
			}
		}
	}
}