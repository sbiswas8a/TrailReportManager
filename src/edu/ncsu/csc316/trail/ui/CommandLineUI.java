/**
 * 
 */
package edu.ncsu.csc316.trail.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc316.trail.manager.TrailReportManager;

/** CommandLineUI class that provides a command line based interface to the user
 * to run the TrailReportManager program
 * 
 * @author Sumit Biswas
 *
 */
public class CommandLineUI {
	
	/** Manager instance used for the program */
	private static TrailReportManager manager = null;
	
	/** Starts the program
	 * 
	 * @param args command line arguments 
	 * @throws FileNotFoundException if the input file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("________________________________________________________________\n");
		System.out.println("                       TRAIL REPORT MANAGER");
		System.out.println("________________________________________________________________\n\n");
		System.out.println("*** Enter Q at any time to exit the program ***\n");
		System.out.println("Please enter path to the potential trails data file you wish to use\n");
		String input = scanner.next();
		if (input.equalsIgnoreCase("q")) {
			scanner.close();
			System.exit(0);
		}
		
		File file = new File(input);
		while (!file.exists()) {
			System.out.println("\nCould not find file, please try again (OR enter Q to exit)\n");
			System.out.println("Please enter path to the potential trails data file you wish to use\n");
			input = scanner.next();
			if (input.equalsIgnoreCase("q")) {
				scanner.close();
				System.exit(0);
			}
			file = new File(input);
		}
		
		try {
			manager = new TrailReportManager(input);
		} catch (IllegalArgumentException e) {
			System.out.println("The provided input file does not contain any trail data.");
			System.exit(0);
		}
			
		
		do {
			System.out.println("\nCHOOSE ONE OF THE OPTIONS BELOW:\n");
			System.out.println("          # Enter 1 to get least costly trails report");
			System.out.println("          # Enter 2 to get restroom locations");
			System.out.println("          # Enter 3 to get trail sign locations\n");
			
			int option = -1;
			
			while (option != 1 && option != 2 && option != 3) {
				input = scanner.next();
				if (input.equalsIgnoreCase("q")) {
					scanner.close();
					System.exit(0);
				}
				try {
					option = Integer.parseInt(input);
				} catch (Exception e) {
					if (input.equalsIgnoreCase("q")) {
						scanner.close();
						System.exit(0);
					}
					System.out.println("Invalid input, please try again\n");
				}
			}
			
			if (option == 1) {
				double number = 0;
				System.out.println("Enter cost per linear foot for constructing trails\n");
				while (number <= 0 && scanner.hasNext() ) {
					input = scanner.next();
					if (input.equalsIgnoreCase("q")) {
						scanner.close();
						System.exit(0);
					}
					try {
						number = Double.parseDouble(input);
					} catch (Exception e) {
						System.out.println("Invalid input, please try again\n");
					}
					if (number <= 0 ) {
						number = 0;
						System.out.println("Construction cost per linear foot must be > $0.00.\n");
					}
				}
				String output = manager.getLeastCostlyTrailsReport(number);
				System.out.println("Enter the filename for output\n");
				String opFile = scanner.next();
				File out = new File(opFile);
				if (!out.exists()) {
					try {
						out.createNewFile();
					} catch (IOException e) {
						System.out.println("Unexpected Error, program terminated");
						scanner.close();
						System.exit(0);
					}
				}
				PrintStream op = null;
				try {
					op = new PrintStream(out);
				} catch (FileNotFoundException e) {
					System.out.println("Unexpected Error, program terminated");
					scanner.close();
					System.exit(0);
				}
				op.print(output);
				System.out.println("\nEnter Q to exit / anything else to continue\n");
			//////////////////////////////////////////////// RESTROOM LOCATIONS  /////////////////////////////////////////////////
			} else if (option == 2) {
				int dist = 0;
				System.out.println("Enter minimum length of trail\n");
				while (dist <= 0 && scanner.hasNext() ) {
					input = scanner.next();
					if (input.equalsIgnoreCase("q")) {
						scanner.close();
						System.exit(0);
					}
					try {
						dist = Integer.parseInt(input);
					} catch (Exception e) {
						System.out.println("Invalid input, please try again\n");
					}
					if (dist <= 0 ) {
						dist = 0;
						System.out.println("Trail length must be > 0 feet.");
					}
				}			
				String output = manager.getRestroomLocations(dist);
				System.out.println("Enter the filename for output\n");
				String opFile = scanner.next();
				File out = new File(opFile);
				if (!out.exists()) {
					try {
						out.createNewFile();
					} catch (IOException e) {
						System.out.println("Unexpected Error, program terminated");
						scanner.close();
						System.exit(0);
					}
				}
				PrintStream op = null;
				try {
					op = new PrintStream(out);
				} catch (FileNotFoundException e) {
					System.out.println("Unexpected Error, program terminated");
					scanner.close();
					System.exit(0);
				}
				op.print(output);
				System.out.println("\nEnter Q to exit / anything else to continue\n");
			//////////////////////////////////////////////// TRAIL SIGN LOCATIONS /////////////////////////////////////////////////	
			} else {
				String output = manager.getTrailSignLocations();
				System.out.println("Enter the filename for output: ");
				String opFile = scanner.next();
				File out = new File(opFile);
				if (!out.exists()) {
					try {
						out.createNewFile();
					} catch (IOException e) {
						System.out.println("Unexpected Error, program terminated");
						scanner.close();
						System.exit(0);
					}
				}
				PrintStream op = null;
				try {
					op = new PrintStream(out);
				} catch (FileNotFoundException e) {
					System.out.println("Unexpected Error, program terminated");
					scanner.close();
					System.exit(0);
				}
				op.print(output);
				System.out.println("\nEnter Q to exit / anything else to continue");
			}
		} while (!scanner.next().equalsIgnoreCase("q"));
		
	}
}
