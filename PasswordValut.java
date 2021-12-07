import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author Jorge Zaquitzal
 * CMSC 413 Intro to Cyber Security 
 * Project 1
 */
public class PasswordValut {

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		String choice = null;
		boolean cont = true;
		String choice1;
		String choice2;

		
		/*
		 * Prompt to the user where the user can either 
		 * create a new password file or add to an 
		 * existing password file.
		 */
		System.out.println("Welcome to the password vault: \n");

		/*outter loop that takes user 
		 *input until a valid selection has been made.
		 */
		while (cont) {

			System.out.println(
			"Please 1 to add existing password file.\n" 
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

				decryptFile();

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
						printFileContents();
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
						deleteRecord(website);
						System.out.println("Account stating with " + website + " has been deleted.\n");

						/*
						 * Choice number 3 allows user
						 * add to the file.
						 */
					} else if (choice1.equals("3")) {

						dataInputLoop();

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
				encryptFile();
				File file = new File("password.txt");
				file.delete();
				cont = false;

			} else if (choice.equals("2")) {

				createFile();
				dataInputLoop();
				boolean loopChoice = true;

				while (loopChoice) {

					System.out.println(
					"To view Username and passwords press 1\n" 
					+ "To delete entry press 2\n"
					+ "To Add to list press 3\n" + "To exit press 4");
					choice2 = in.next();

					if (choice2.equals("1")) {

						System.out.println();
						printFileContents();
						System.out.println();

					} else if (choice2.equals("2")) {

						System.out.println(
						"Enter the website whos username and password\n" 
						+ "you'd like to delete:\n");
						String website = in.next();
						deleteRecord(website);
						System.out.println("Account stating with " + website + " has been deleted.\n");

					} else if (choice2.equals("3")) {

						dataInputLoop();

					} else if (choice2.equals("4")) {

						System.out.println("Exiting!");
						loopChoice = false;

					}
				}
				
				encryptFile();
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
	/*
	 * This method deletes a row identified by the 
	 * website which is entered by the user.
	 */
	private static void deleteRecord(String company) {

		int indexOfCompany = 0;
		String del = " ";
		String currentLine;
		String data[];
		String tempFile = "temp.txt";

		File oldFile = new File("password.txt");
		File newFile = new File(tempFile);

		try {

			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			FileReader fr = new FileReader("password.txt");
			BufferedReader br = new BufferedReader(fr);

			while ((currentLine = br.readLine()) != null) {
				data = currentLine.split(del);
				if (!(data[indexOfCompany].equalsIgnoreCase(company))) {
					pw.println(currentLine);

				}
			}
			pw.flush();
			pw.close();
			fr.close();
			br.close();
			bw.close();
			fw.close();

			oldFile.delete();
			File dump = new File("password.txt");
			newFile.renameTo(dump);

		} catch (Exception e) {
			System.out.println("Error Occured.Terminating program. ");
		}
	}
	/*
	 * This method iterated though the file to print 
	 * its contents.
	 */
	private static void printFileContents() throws IOException {

		// variable declaration
		int ch;

		// check if File exists or not
		FileReader fr = null;
		try {
			fr = new FileReader("password.txt");
		} catch (FileNotFoundException fe) {
			System.out.println("File not found");
		}

		// read from FileReader till the end of file
		while ((ch = fr.read()) != -1) {
			System.out.print((char) ch);
		}
		// close the file
		fr.close();
	}
	/*
	 * This method creates the file if it dosn't already exits.
	 */
	public static void createFile() throws IOException {

		System.out.println("Creating new password file.\n");
		File file = new File("encrypted_password.txt");

		if (!file.exists()) {
			file.createNewFile();
		} else {
			System.out.println("File already exists.");
			System.exit(0);
		}
	}
	/*
	 * This method is the loop that takes users input to 
	 * fill the file with in this order:
	 * **website username password**
	 */
	public static void dataInputLoop() throws IOException {

		String file = "password.txt";
//		File file = new File("password.txt");
		// created filewriter objected called fw
		FileWriter fw = new FileWriter(file, true);
		// created a printwriter object called pw
		PrintWriter pw = new PrintWriter(fw);

		Scanner in = new Scanner(System.in);
		String website;
		String userName = "";
		String password = "";
		String choice;
		boolean loop = true;

		while (loop) {

			System.out.println("Please enter Website");
			website = in.nextLine();
			System.out.println("Please enter username");
			userName = in.nextLine();
			System.out.println("Please enter password");
			password = in.nextLine();

			pw.printf("%s %s %s\n", website, userName, password);

			System.out.println("Data has been saved to file.");
			System.out.println();
			System.out.println("Would you like to enter more information ? Yes or no\n");
			choice = in.nextLine();

			if (choice.equals("yes")) {
				loop = true;
			} else {
				loop = false;
			}
		}
		pw.close();
	}
	/*
	 * This method encryptes the file with a master key that
	 * is created by the user
	 */
	public static void encryptFile() {

		Scanner in = new Scanner(System.in);

		System.out.println("Please create 8 digit secret key to encrypt file: ");
		String key = in.nextLine();
		File plaintext = new File("password.txt");
		File encrypted = new File("encrypted_password.txt");
		try {
			encryptDecrypt(key, Cipher.ENCRYPT_MODE, plaintext, encrypted);
			System.out.println("Encryption Complete\n");
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * This decryptes the file with a master key that
	 * is created by the user
	 */
	public static void decryptFile() {
		File decrypted = new File("password.txt");
		File encrypted = new File("encrypted_password.txt");
		Scanner in = new Scanner(System.in);
		String passKey;
		boolean authen = false;
		System.out.println("Enter the secret 8 digit key to decrypt file: ");
		passKey = in.nextLine();
		try {
			encryptDecrypt(passKey, Cipher.DECRYPT_MODE, encrypted, decrypted);
			System.out.println("Decryption Complete\n");
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * This method encryptes the entire file.
	 */
	public static void encryptDecrypt(String key, int cipherMode, File in, File out) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException {

		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = skf.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		if (cipherMode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
			CipherInputStream cis = new CipherInputStream(fis, cipher);
			write(cis, fos);
		} else if (cipherMode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);
			write(fis, cos);
		}
	}
	/*
	 * This method write the encrypted text to the encrypted file.
	 */
	private static void write(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[64];
		int numOfBytesRead;
		while ((numOfBytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, numOfBytesRead);
		}
		out.close();
		in.close();
	}

}
