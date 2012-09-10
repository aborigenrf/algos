package github.com.ekis.algorithms.chapter1.impl;

import static github.com.ekis.algorithms.chapter1.api.UnionFindEnum.*;

import edu.princeton.algs4.Stopwatch;
import edu.princeton.stdlib.In;
import edu.princeton.stdlib.StdOut;
import github.com.ekis.algorithms.chapter1.api.AbstractUnionFind;

/**
 * Implements quick union find.
 * 
 * @author Erik
 *
 */
public class QuickFind extends AbstractUnionFind {

	static double pctCompleted = 0;
	
	/**
	 * Constructs a set of N components.
	 * 
	 * @param N
	 */
	public QuickFind(int N) {
		super(N);
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#union(int, int)
	 */
	@Override
	public void union(int p, int q) {
		// put p and q into the same component
		int pID = find(p);
		int qID = find(q);
		
		if (pID == qID) 
			return; // nothing to do if p and q are already connected
		
		for (int i = 0; i < id.length; i++) {
			if (id[i] == pID) id[i] = qID;
		}
		count--;
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#find(int)
	 */
	@Override
	public int find(int p) {
		return id[p];
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#connected(int, int)
	 */
	@Override
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#main(java.lang.String[])
	 */
	public static void main(String[] args) {
		String filePath = MEDIUM_UF.value();
		//String filePath = "/Dev/git_repo/algorithms/algs4-infrastructure/algs4-stdlib/src/main/resources/test.txt";
		int[] ints = In.readInts(filePath);
		int len = ints.length;
		
		Stopwatch stopwatch = new Stopwatch();
		// Solve dynamic connectivity problem on StdIn.
		QuickFind uf = new QuickFind(ints[0]);
		
		for (int i = 1; i < (len - 1); i += 2) { // read number of sites
			int p = ints[i];
			int q = ints[i + 1]; // read pair to connect
			
			if (uf.connected(p, q)) // ignore if connected
				continue;
			
			uf.union(p, q); // combine components
			//StdOut.println(p + " " + q); // print connection
			
			double progress = Math.round(((double) i / (double) len) * 100);
			if ((progress % 3 == 0 || progress % 5 == 0) && (progress != pctCompleted)) {
				pctCompleted = progress;
				StdOut.println("Completed -> " + String.format("%-5.0f", pctCompleted) + " %");
			}
		}
		StdOut.println(uf.count() + " components - task completed in: " + stopwatch.elapsedTime() + " sec.");
	}
}