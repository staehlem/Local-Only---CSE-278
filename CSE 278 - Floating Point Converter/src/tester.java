import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class tester {
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
		out = new PrintWriter(new File("output.txt"));
		
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
		} else {
			ret += "0";
		}
		dataIn = dataIn.substring(1);  // Since first char is resolved, you can remove it 
		
		String[] leftAndRightOfTheDecimal = dataIn.split(".");  // The left and right hand side of the float are handled differently and therefore should be split and handled separately 
		ret += normalConversionToBinary(leftAndRightOfTheDecimal[0]);
		return ret;
	}

	/**
	 * Converts a base 10 integer in string representation to a binary string
	 * @param val integer in string form
	 * @return return binary representation of string integer
	 */
	private static String normalConversionToBinary(String val) {
		String convertedValue = "";
		long remainder = 0;
		
		while(Integer.parseInt(val) > 0) {
			remainder = (Integer.parseInt(val) % 2);	
			val = Integer.parseInt(val)/2 + "";
			convertedValue = remainder + convertedValue;
		}
		
		return convertedValue;
	}
}	