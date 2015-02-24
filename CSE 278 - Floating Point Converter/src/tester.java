import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class tester {
	public static Scanner in;
	public static PrintWriter out;
	
	public static void main(String[] args) {
		try { readAndConvert(); }
		catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
	}
	
	private static void readAndConvert() throws Exception {
		String dataIn = "", dataOut = "";
		in = new Scanner(new File("nums.txt"));
		out = new PrintWriter(new File("output.txt"));
		
		while(in.hasNextLine()) {
			dataIn = in.nextLine();
			// convert dataIn to dataOut
			out.println(dataOut);
		}
		
		in.close();
		out.close();
	}
}