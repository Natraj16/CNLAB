package CN;
import java.util.Scanner;
import java.io.*;

public class CRC {

	public static void main(String args[]) {

	Scanner sc = new Scanner(System.in);

	// Input Data Stream

	System.out.print("Enter the number of bits in the message: ");
	int messageLength = sc.nextInt();
	int[] message = new int[messageLength];
	System.out.println("Enter message bits (space-separated 0s and 1s): ");
	for (int i = 0; i < messageLength; i++) {
	message[i] = sc.nextInt();
	}

	System.out.print("Enter the number of bits in the generator: ");
	int generatorLength = sc.nextInt();
	int[] generator = new int[generatorLength];
	System.out.println("Enter generator bits (space-separated 0s and 1s): ");
	for (int i = 0; i <generatorLength; i++) {
	generator[i] = sc.nextInt();
	}

	// Prepare data array (message + zeros for CRC calculation)
	int[] data = new int[messageLength + generatorLength - 1];
	int[] divisor = new int[generatorLength];
	System.arraycopy(message, 0, data, 0, messageLength); 
	System.arraycopy(generator, 0, divisor, 0, generatorLength); 

	// Calculation of CRC
	for (int i = 0; i < messageLength; i++) {
	if (data[i] == 1) {
	for (int j = 0; j < divisor.length; j++) {
	data[i + j] ^= divisor[j];
	}
	}
	}

	// Display the CRC (checksum) code
	System.out.print("The checksum code is: ");
	for (int i = 0; i <messageLength; i++) {
	data[i] = message[i]; // Restore the original message part
	}
	for (int i = 0; i < data.length; i++) {
	System.out.print(data[i]);
	}
	System.out.println();

	// Check for input CRC code
	System.out.print("Enter the number of bits in the checksum code: ");
	int checksumLength = sc.nextInt();
	int[] checksum = new int[checksumLength];

	System.out.println("Enter checksum code bits (space-separated 0s and 1s): ");
	for (int i = 0; i < checksumLength; i++) {
	checksum[i] = sc.nextInt();
	}

	// Copy the input checksum into the data array for validation
	int[] receivedData = new int[checksumLength + generatorLength - 1];
	System.arraycopy(checksum, 0, receivedData, 0, checksumLength);
	for (int i = 0; i < generatorLength; i++) {
	divisor[i] = generator[i]; // Use the same generator
	}

	// Calculation of remainder
	for (int i = 0; i < checksumLength; i++) {
	if (receivedData[i] == 1) {
	for (int j = 0; j <divisor.length; j++) {
	receivedData[i + j] ^= divisor[j];
	}
	}
	}

	// Display validity of data
	boolean valid = true;

	for (int i = 0; i < receivedData.length; i++) {
	if (receivedData[i] == 1) {
	valid = false;
	break;
	}
	}

	if (valid) {
	System.out.println("Data stream is valid");
	} else {
	System.out.println("Data stream is invalid. CRC error occurred.");
	}

	sc.close();
	}
	}
