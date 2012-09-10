package github.com.ekis.algorithms.chapter1.impl;

import static github.com.ekis.algorithms.chapter1.api.UnionFindEnum.*;

import edu.princeton.algs4.Stopwatch;
import edu.princeton.stdlib.In;
import edu.princeton.stdlib.StdOut;
import github.com.ekis.algorithms.chapter1.api.AbstractUnionFind;

/**
 * Implements quick union.
 * 
 * @author Erik
 *
 */
public class QuickUnion extends AbstractUnionFind {

	static double pctCompleted = 0;
	
	/**
	 * Constructs a set of N components.
	 * 
	 * @param N
	 */
	public QuickUnion(int N) {
		super(N);
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#union(int, int)
	 */
	@Override
	public void union(int p, int q) {
		// give p and q the same root
		int i = find(p);
		int j = find(q);
		
		if (i == j) return;
		
		id[i] = j;
		
		count--;
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#find(int)
	 */
	@Override
	public int find(int p) {
		// find component name
		while (p != id[p]) {
			p = id[p];
		}
		return p;
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
		int[] ints = In.readInts(filePath);
		int len = ints.length;
		
		Stopwatch stopwatch = new Stopwatch();
		// Solve dynamic connectivity problem on StdIn.
		QuickUnion uf = new QuickUnion(ints[0]);
		
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