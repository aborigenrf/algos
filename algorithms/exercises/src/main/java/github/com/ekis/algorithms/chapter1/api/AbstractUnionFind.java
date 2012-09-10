package github.com.ekis.algorithms.chapter1.api;

/**
 * @author Erik
 *
 */
public abstract class AbstractUnionFind {
	/**
	 * Access to component ID (site indexed)
	 */
	protected int[] id;
	
	/**
	 * Number of components.
	 */
	protected int count;
	
	/**
	 * Initialize N sites with integer names [0, N-1]
	 * 
	 * @param N number of sites
	 */
	protected AbstractUnionFind(int N) {
		count = N;
		id = new int[N];
		for (int i = 0; i < N; i++) // initialize array with values
			id[i] = i;
	}
	
	/**
	 * Adds a connection between params.
	 * 
	 * @param p
	 * @param q
	 */
	public abstract void union(int p, int q);

	
	/**
	 * Answers component identifier for param p.
	 * 
	 * @param p
	 * @return component identifier
	 */
	public abstract int find(int p);

	/**
	 * Answers whether params are in the same component.<br>
	 * 
	 * @param p
	 * @param q
	 * @return <b>TRUE</b> - if params in the same component<br><b>FALSE</b> - if params are NOT in the same component.
	 */
	public abstract boolean connected(int p, int q);
	
	/**
	 * Answers number of components in adjunct data structure.
	 * 
	 * @return number of components
	 */
	public int count() {
		return count;
	}
}
