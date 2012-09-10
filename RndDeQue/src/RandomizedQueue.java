import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements a randomized queue using a resizing-array implementation. 
 * 
 * @author ekis
 * @param <Item>
 * 
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;        // queue elements
    private int N = 0;           // number of elements on queue
    private int last  = 0;       // index of next available slot
	private Iterator<Item> it;
	
	/**
	 * Constructs an empty randomized queue.
	 */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		queue = (Item[]) new Object[2];
		it = iterator();
	}

	/**
	 * Answers whether the queue is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * Returns the number of items on the queue.
	 * 
	 * @return
	 */
	public int size() {
		return N;
	}

	/**
	 * Adds an item to the queue.
	 * 
	 * @param item
	 */
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException();
		if (N == queue.length) 
			resize(2 * queue.length);
		queue[N++] = item;
	}

	/**
	 * Deletes and returns a random item from the queue.
	 * 
	 * 
	 * @return
	 */
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		
		Item item = null;
		if (it.hasNext())
			item = it.next();
		else
			throw new NoSuchElementException();
		
		if (N > 0 && N == queue.length / 4)
			resize(queue.length / 2);
		return item;
	}

	/**
	 * Returns (but does not delete) a random item from the queue.
	 * 
	 * @return
	 */
	public Item sample() {
		if (N == 0) throw new NoSuchElementException();
		int rnd = StdRandom.uniform(0, N);
		return queue[rnd];
	}

	/*
	 * Return an independent iterator over items in random order.
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Item> iterator() {
		return new RandomArrayIterator();
	}
	
	/**
	 * Hint: Use an array representation (with resizing). To remove an item,
	 * swap one at a random position (indexed 0 through N-1) with the one at the
	 * last position (index N-1). Then delete and return the last object, as in
	 * ResizingArrayStack.
	 * 
	 * @author ekis
	 * 
	 */
	private class RandomArrayIterator implements Iterator<Item> {
		Item[] rndArray;
		
		@Override
		public boolean hasNext() {
			return !isEmpty();
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			// poor-man's shuffling
			rndArray = queue;
			int rnd = StdRandom.uniform(0, N);
			exch(rndArray, rnd, N - 1);
			
			last = N;
			Item item = rndArray[--last];
			rndArray[last] = null;
			queue = rndArray;
			N--;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * @param max
	 */
	@SuppressWarnings("unchecked")
	private void resize(int max) {
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++) {
			temp[i] = queue[i];
		}
		queue = temp;
	}
	
	/**
	 * @param array
	 * @param i
	 * @param j
	 */
	private void exch(Item[] array, int i, int j) {
		Item temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	/**
	 * Unit test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int k = 100;
		int p = 100;
		int[] test = new int[p];
		RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
		
		// load up test array
		for (int i = 0; i < p; i++) {
			test[i] = i; 
		}
		
		// enqueue call
		System.out.println("ENQ -> ");
		for (int i = 0; i < k; i++) {
			q.enqueue(test[i]);
			System.out.print(test[i] + " ");
			Iterator it1 = q.iterator();
			Iterator it2 = q.iterator();
			assert (it2.next() != it1.next());
		}
		
		// dequeue call
		System.out.println();
		
		System.out.println("DEQ -> ");
		for (int i = 0; i < k; i++) {
			System.out.print(q.dequeue() + " ");
			Iterator it1 = q.iterator();
			Iterator it2 = q.iterator();
			assert (it2.next() != it1.next());
		}
	}
}