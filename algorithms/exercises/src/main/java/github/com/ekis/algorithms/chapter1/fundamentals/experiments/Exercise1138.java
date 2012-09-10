package github.com.ekis.algorithms.chapter1.fundamentals.experiments;

import edu.princeton.algs4.BinarySearch;
import edu.princeton.algs4.Stopwatch;
import edu.princeton.stdlib.In;
import edu.princeton.stdlib.StdIn;
import edu.princeton.stdlib.StdOut;

/**
 * 1.1.38 Binary search versus brute-force search. Write a program
 * BruteForceSearch that uses the brute-force search method given on page 48 and
 * compare its running time on your computer with that of BinarySearch for
 * largeW.txt and largeT.txt.
 * 
 * @author Erik
 * 
 */
public class Exercise1138 {
	
	private static final String LARGE_W = "/github/com/ekis/algorithms/chapter1/fundamentals/experiments/largeW.txt";
	//private static final String LARGE_T = "/github/com/ekis/algorithms/chapter1/fundamentals/experiments/largeT.txt";

	public static int rankBruteForce(int key, int[] a) {
		for (int i = 0; i < a.length; i++)
			if (a[i] == key) return i;
		return -1;
	}

	public static void main(String[] args) {
		int[] whitelist = In.readInts(LARGE_W);
		//int[] transactions = In.readInts(LARGE_T);
		
		// read key; print if not in whitelist
        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            Stopwatch sw = new Stopwatch();
            int found = rankBruteForce(key, whitelist);
            StdOut.println("Brute force search took -> " + sw.elapsedTime() + " sec.");
            
            if (found == -1)
            	StdOut.println("BruteForce search didn't find this key ->" + key);
            
            BinarySearch.main(new String[] {LARGE_W});
            break;
        }
	}
}