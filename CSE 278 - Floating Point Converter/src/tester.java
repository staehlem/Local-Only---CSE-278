import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tester {
	public static Scanner in;
	public static PrintWriter out;
	
	public static void main(String[] args) {
		try { readAndConvert(); }  // Call method readAndConvert, this does the bulk of the work in the program
		catch (Exception e) { System.out.println("Error: " + e.getMessage()); }  // Catch exception if thrown by readAndConvert, print message
	}
	
	/**
	 * Reads a file "nums.txt" and outputs the converted version of that file line by line to a file named "converted.txt"
	 * @throws Exception general exception handler
	 */
	private static void readAndConvert() throws Exception {
		String dataIn = "", dataOut = "";
		in = new Scanner(new File("nums.txt"));
		out = new PrintWriter(new File("converted.txt"));
		
		// This loop handles the flow of the converter.  It determines where the read in string should go in terms of converter method.
		while(in.hasNextLine()) {
			dataIn = in.nextLine();
			
			/*
			 * This if statement determines which way the conversion goes based on string length since the binary numbers are 32 bits
			 */
			if(dataIn.length() == 32) {
				dataOut = binaryToFloat(dataIn); // convert dataIn to dataOut
			} else {
				dataOut = floatToBinary(dataIn); // convert dataIn to dataOut
			}
			
			out.println(dataOut);
		}
		// close resources
		in.close();
		out.close();
	}
	
	private static String binaryToFloat(String dataIn) {
		String ret = "";
		
		
		
		
		return ret;
	}

	private static String floatToBinary(String dataIn) {
		String ret = "";
		
		if(dataIn.charAt(0) == '-'){  // Determine the first binary # based on neg or pos value
			ret += "1";
			dataIn = dataIn.substring(1);  // Since first char is resolved, you can remove it 
		} else {
			ret += "0";
		}
				
		System.out.println();
		
		String[] leftAndRightOfTheDecimal = dataIn.split("\\.");  // The left and right hand side of the float are handled differently and therefore should be split and handled separately 
		ret += normalConversionToBinary(leftAndRightOfTheDecimal[0]); // convert left of the decimal
		ret += floatingPointConversionToBinary(leftAndRightOfTheDecimal[1]);  // convert right of the decimal
		
		return ret; // return converted string
	}

	/**
	 * Conversion for the right side of the decimal
	 * @param val
	 * @return
	 */
	private static String floatingPointConversionToBinary(String val) {
		val = "0." + val;	// This puts a zero and a decimal at the front of the integer that the split got rid of.  Makes the value of val a decimal #
		String convertedValue = ".";  // the decimal was lost with the split from floatToBinary, added here
		float remainder = 0;
		int i = 0;  // counter
		
																																	//System.out.println("Made it to RIGHT side conversion " + val);
																																	//System.out.println(Float.parseFloat(val));
		
		
		while(Float.parseFloat(val) > 0 && i < 24) {  // max of 23 digits in a 32 bit machine so 'i' must be lower than 24
			
			if((Float.parseFloat(val) * 2) >= 1) {		// check remainder
				remainder = ((2 * Float.parseFloat(val))-1); // set remainder
																																	//System.out.println("what is the remainder right after i set it?? ----------> " + remainder);
				convertedValue += "1";
			} else {
				remainder = (2 * Float.parseFloat(val)); // set remainder
																																	//System.out.println("what is the remainder right after i set it in the else brach**?? ----------> " + remainder);
				convertedValue += "0";
			}
																																	//System.out.println(Float.parseFloat(val) + " ~~~~~~~~~~~~~~ after the else  ");
																																	//System.out.println("what is the remainder right before val is updated?? ----------> " + remainder);
			val = remainder + ""; //update val
																																	//System.out.println(Float.parseFloat(val) + " ~~~~~~~~~~~~~~ after updateing val");
			i++; // increment counter
		}
		
		return convertedValue; // return converted value
	}

	/**
	 * Converts a base 10 integer in string representation to a binary string
	 * @param val integer in string form
	 * @return return binary representation of string integer
	 */
	private static String normalConversionToBinary(String val) {
		String convertedValue = "";
		long remainder = 0;
																																	//System.out.println("Made it to LEFT side conversion " + val);
		while(Integer.parseInt(val) > 0) {
			remainder = (Integer.parseInt(val) % 2);	
			val = Integer.parseInt(val)/2 + "";
			convertedValue = remainder + convertedValue;
		}
																																	//System.out.println(convertedValue);
		return convertedValue;
	}
}	