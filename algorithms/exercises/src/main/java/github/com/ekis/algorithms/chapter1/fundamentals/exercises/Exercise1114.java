package github.com.ekis.algorithms.chapter1.fundamentals.exercises;

import edu.princeton.stdlib.StdOut;


/**
 * 
 * 1.1.14  Write a static method lg() that takes an int value N as argument and returns 
 * the largest int not larger than the base-2 logarithm of N. Do not use {@link Math}.
 * @author ekis
 *
 */
public class Exercise1114 {
	
	public static int lg(int N) {
		String binary = null;
		if (N > 0) {
			binary = Integer.toBinaryString(N);
			int x = binary.length() - 1; // more formally, x = floor(Log_2(N))
			// check -> StdOut.println("N = " + N + "; Log_2(N) = " + Math.log10(N)/Math.log10(2));
			// StdOut.println("Binary representation -> " + binary + "; result = " + x);
			StdOut.println("N = " + N + "; result = " + x); 
			return x;
		} else {
			return 0;
		}
	}
	
	public static void main(String args[]) {
		int x = 15;
		lg(x);
	}
}
