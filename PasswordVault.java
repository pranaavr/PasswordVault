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
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author Jorge Zaquitzal
 * @author Pranaav Rao
 * CMSC 413 Intro to Cyber Security 
 * Project 1
 */

 public class PasswordVault {
	
	/*
	 * This method deletes a row identified by the 
	 * website which is entered by the user.
	 */
	public static void deleteRecord(String company) {

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
	public static void printFileContents() throws IOException {
		// variable declaration
		int ch;
		
		// check if File exists or not
		FileReader fr = null;
		fr = new FileReader("password.txt");
		
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

		File file = new File("encrypted_password.txt");

		if (!file.exists()) {
			file.delete();
		}
		file.createNewFile();
	}
	
	/*
	 * This method is the loop that takes users input to 
	 * fill the file with in this order:
	 * **website username password**
	 */
	public static void dataInput(String website, String userName, String password) throws IOException {
		String file = "password.txt";
//		File file = new File("password.txt");
		// created filewriter objected called fw
		FileWriter fw = new FileWriter(file, true);
		// created a printwriter object called pw
		PrintWriter pw = new PrintWriter(fw);

		pw.printf("%s %s %s\n", website, userName, password);

		pw.close();
	}
	
	/*
	 * This method encryptes the file with a master key that
	 * is created by the user
	 */
	public static void encryptFile(String key) {
		File plaintext = new File("password.txt");
		File encrypted = new File("encrypted_password.txt");
		try {
			encryptDecrypt(key, Cipher.ENCRYPT_MODE, plaintext, encrypted);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This decryptes the file with a master key that
	 * is created by the user
	 */
	public static void decryptFile(String passkey) {
		File decrypted = new File("password.txt");
		File encrypted = new File("encrypted_password.txt");
		try {
			encryptDecrypt(passkey, Cipher.DECRYPT_MODE, encrypted, decrypted);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method encrypts the entire file.
	 * DES is a block cipher, an algo that takes a fixed length string 
	 * of plain text bits and transforms it through some operations into a byte string
	 * Block size is 64 bits
	 * Uses a master key to customize transformation of data 
	 * so that decryption is performed with the key that is created
	 */
	public static void encryptDecrypt(String key, int cipherMode, File in, File out) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException {
		
		//creating file input stream to read/write to files
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);

		//Passes in the bytes of the key
		//Creates a DESKeySpec object using the first 8 bytes 
		//in key as the key material for the DES key.
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

		//Key factories are used to convert keys into key specifications
		//DES-(Data Encryption Standard) mechanism that can encrypt and decrypt the data
		//It accepts the plaintext in 64-bit blocks and changes it into the ciphertext 
		//that uses the 64-bit keys to encrypt the data. 
		//The algorithm uses the same key to encrypt and decrypt the data.
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = skf.generateSecret(desKeySpec);
		
		//cipher mode-Electronic code book/PKCS5 padding- PKCS5 padding is defined for 8-byte block sizes
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		
		//Here is where I check if were using encryption or decryption based on the cipher mode
		if (cipherMode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));//SHA1PRNG - hash algo - PRNG = Pseudo Random Number Generator
			CipherInputStream cis = new CipherInputStream(fis, cipher);//cipher input stream takes in file input stream and cipher 
			write(cis, fos);//write to file 
			//ELSE decrypt cipher mode
		} else if (cipherMode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));//SHA1PRNG - hash algo - PRNG = Pseudo Random Number Generator
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);//cipher input stream takes in file input stream and cipher 
			write(fis, cos);
		}
	}
	
	/*
	 * This method write the encrypted text to the encrypted file.
	 */
	private static void write(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[64]; //64 bytes
		int numOfBytesRead;
		while ((numOfBytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, numOfBytesRead);
		}
		out.close();
		in.close();
	}

}
