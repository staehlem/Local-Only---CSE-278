import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tester {
	public static Scanner in;
	public static PrintWriter out;
	private static final String BASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";  // Used with index of to allow for easy conversion of letters to base value integers
	
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
		
		if(dataIn.charAt(0) == 1) {
			ret += "-";
		}
		dataIn = dataIn.substring(1);
		
		double resultBias = Integer.parseInt(convertTo10(dataIn.substring(0, 8))) - 127;  //This is used in main equation
		
		dataIn.substring(8);
		double values = 1.0;
		for(int i = 0; i < 24; i++) {
			if(dataIn.charAt(i) == 1) {
				values += Math.pow(2.0, -i);
			}
		}
		ret += (double)(values * Math.pow(2.0, resultBias));
		
		return ret;
	}

	private static String floatToBinary(String dataIn) {
		String ret = "";
		String workingValue = "";
		
		if(dataIn.charAt(0) == '-'){  // Determine the first binary # based on neg or pos value
			ret += "1";
			dataIn = dataIn.substring(1);  // Since first char is resolved, you can remove it 
		} else {
			ret += "0";
		}
		
		String[] leftAndRightOfTheDecimal = dataIn.split("\\.");  						// The left and right hand side of the float are handled differently and therefore should be split and handled separately 
		workingValue += normalConversionToBinary(leftAndRightOfTheDecimal[0]); 			// convert left of the decimal
		workingValue += floatingPointConversionToBinary(leftAndRightOfTheDecimal[1]);   // convert right of the decimal
		
		// workingValue now represents the non- 32 bit converted representation of our floating point #
		
																																				//System.out.println(workingValue);
		int numberOfSpacesTheDecimalMovedForNormalization = 0;
		if(workingValue.charAt(0) == '.') {		//make sure '.' is in the second position (index of 1)
			workingValue = "0" + workingValue;
		}
		
		if(workingValue.indexOf('.') != 1) {		//normalize the value
			numberOfSpacesTheDecimalMovedForNormalization = workingValue.indexOf('.') - 1;
			workingValue = workingValue.substring(0, workingValue.indexOf('.')) + workingValue.substring(workingValue.indexOf('.') + 1);		//use substrings to "move" the decimal
			workingValue = workingValue.substring(0, 1) + "." + workingValue.substring(1);
		}
																																				//System.out.println(workingValue);
		
		int bias = 127 + numberOfSpacesTheDecimalMovedForNormalization;  // set the bias
																																				//ret += normalConversionToBinary(bias + "");
		if(normalConversionToBinary(bias + "").length() < 8){
			ret += createStringOfZeroes(8 - normalConversionToBinary(bias + "").length()) + normalConversionToBinary(bias + "");
		} else {
			ret += normalConversionToBinary(bias + "");
		}
		
		/*
		 * Now we need to add the last 23 bits on
		 */
		String replacementBinaryNums = workingValue.substring(2, 25);	//the ints that will be replacing the zeros, restricted to 23 bits

																																				//System.out.println(replacementBinaryNums);
		ret += replacementBinaryNums;
		return ret; // return converted string
	}

	// this ensures that the 8-bit conversion of the bias has the proper # of characters
	private static String createStringOfZeroes(int i) {
		String str = "";
		for(int j = 0; j < i; j++)
			str += "0";
		return str;
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
	
	/**
	 * This method converts a value of a base other than ten and converts it to that integer
	 * @param from starting base, not 10
	 * @param to 10 in this case
	 * @param val string representation of the current value at the current base
	 * @return return new value at base 10
	 */
	private static String convertTo10(String val) {
	
		long total = 0;	// long because total could get very large depending on val
		
		while(val.length() > 0) {
			total += BASE.indexOf(val.charAt(0)) * Math.pow(2, val.length()-1);	// adds an index of the global character string above 
			val = val.substring(1);		// chop off index 0 and loop again
		}
		
		return "" + total;
	}
}	