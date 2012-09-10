package github.com.ekis.algorithms.chapter1.fundamentals.exercises;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import edu.princeton.stdlib.StdIn;
import edu.princeton.stdlib.StdOut;

/**
 * 1.1.21  Write a program that reads in lines from standard input with each line containing 
 * a name and two integers and then uses printf() to print a table with a column of 
 * the names, the integers, and the result of dividing the ﬁrst by the second, accurate to 
 * three decimal places. You could use a program like this to tabulate batting averages for 
 * baseball players or grades for students.
 * 
 * @author Erik
 *
 */
public class Exercise1121 {

	public static void main(String args[]) {
		int rows = 10;
		int cols = 4;
		String[][] table = new String[rows][cols];
		int row = 0;
		while (!StdIn.isEmpty()) {
			table[row][0] = StdIn.readString();
			double col2 = StdIn.readDouble();
			double col3 = StdIn.readDouble();
			double col4 = (col2 / col3);
			
			DecimalFormat df = new DecimalFormat("#####.##");
			df.setRoundingMode(RoundingMode.HALF_EVEN); // applies to String.format()
			
			table[row][1] = String.valueOf(col2);
			table[row][2] = String.valueOf(col3);
			table[row][3] = String.format("%-5.2f", col4);
			
			if (StdIn.hasNextLine()) {
				row++;
			}
		}
		String header = printString("Name") + printString("Column 1") + printString("Column 2") + printString("Col1/Col2");
		StdOut.println(header);
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < cols; j++) {
				StdOut.printf("%-10s", table[i][j]);
			}
			StdOut.println();
		}
	}
	
	public static void printTable(String name, double col1, double col2) {
		String result = printString(name) + printDouble(col1) + printDouble(col2) + printDouble(Math.round(col1/col2));
		StdOut.println(result);
	}
	
	private static String printString(String name) {
		return String.format("%-10s", name);
	}
	
	private static String printDouble(double value) {
		return String.format("%-10s", String.valueOf(value));
	}
}