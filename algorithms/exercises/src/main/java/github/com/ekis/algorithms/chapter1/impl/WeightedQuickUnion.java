package github.com.ekis.algorithms.chapter1.impl;

import static github.com.ekis.algorithms.chapter1.api.UnionFindEnum.*;

import edu.princeton.algs4.Stopwatch;
import edu.princeton.stdlib.In;
import edu.princeton.stdlib.StdOut;
import github.com.ekis.algorithms.chapter1.api.AbstractUnionFind;

/**
 * Implements weighted quick union.
 * 
 * @author Erik
 *
 */
public class WeightedQuickUnion extends AbstractUnionFind {

	static double pctCompleted = 0;
	
	private int[] sz; //
	
	/**
	 * Constructs a set of N components.
	 * 
	 * @param N
	 */
	public WeightedQuickUnion(int N) {
		super(N);
		sz = new int[N];
		for (int i = 0; i < N; i++) sz[i] = 1;
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
		
		//id[i] = j;
		// weighted union - make smaller root point to larger one
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
		
		count--;
	}

	/* (non-Javadoc)
	 * @see github.com.ekis.algorithms.chapter1.api.AbstractUnionFind#find(int)
	 */
	@Override
	public int find(int p) {
		// find component name
		while (p != id[p]) {
			//id[p] = id[id[p]]; // poor-man's path compression
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
		String filePath = LARGE_UF.value();
		//String filePath = "/Dev/git_repo/algorithms/algs4-infrastructure/algs4-stdlib/src/main/resources/test2.txt";
		int[] ints = In.readInts(filePath);
		int len = ints.length;
		
		Stopwatch stopwatch = new Stopwatch();
		// Solve dynamic connectivity problem on StdIn.
		WeightedQuickUnion uf = new WeightedQuickUnion(ints[0]);
		
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