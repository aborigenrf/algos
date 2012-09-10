package github.com.ekis.algorithms.chapter1.fundamentals.exercises;

import edu.princeton.stdlib.StdOut;

/**
 * 1.1.13 Write a code fragment to print the transposition (rows and columns changed)
 * of a two-dimensional array with M rows and N columns.
 * 
 * @author ekis
 *
 */
public class Exercise1113 {

	private static int[][] transpose(int[][] inputArray) {
		int rows = inputArray.length;
		int cols = inputArray[0].length;
		
		int[][] result = new int[cols][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < inputArray[i].length; j++) {
				result[j][i] = inputArray[i][j];
			}
		}
		return result;
	}
	
	/**
	 * Unit test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 2x2 square
		int[][] array22 = {{1, 2} , {3, 4}};
		transposeAndPrint(array22);
		
		// 3x3 square
		int[][] array33 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		transposeAndPrint(array33);
		
		// 4x4 square
		int[][] array44 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
		transposeAndPrint(array44);
		
		// 1x3 vector
		int[][] array13 = {{1, 2, 3}};
		transposeAndPrint(array13);
		
		// 3x1
		int[][] array31 = {{1}, {2}, {3}};
		transposeAndPrint(array31);
		
		// 2x3
		int[][] array23 = {{1, 2, 3}, {4, 5, 6}};
		transposeAndPrint(array23);
		
		// 3x2
		int[][] array32 = {{1, 2}, {3, 4}, {5, 6}};
		transposeAndPrint(array32);
		
		// 2x4
		int[][] array24 = {{1, 2, 3, 4}, {5, 6, 7, 8}};
		transposeAndPrint(array24);
	}
	
	private static void transposeAndPrint(int[][] array) {
		StdOut.println("Pre-transpose...");
		printArray(array);
		array = transpose(array);
		StdOut.println("Post-transpose...");
		printArray(array);
		StdOut.println("-----------------");
	}
	
	private static void printArray(int[][] array) {
		int rows = array.length;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < array[i].length; j++) {
				StdOut.print(array[i][j] + " ");
			}
			StdOut.println();
		}
	}
}
