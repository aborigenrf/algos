import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements double-ended queue using a doubly-linked list.<br>
 * 
 * @author ekis
 * @param <Item>
 * 
 */
public class Deque<Item> implements Iterable<Item> {
	/**
	 * Number of items in deque.
	 */
	private int N;
	/**
	 * Beggining of the queue.
	 */
	private Node first;
	/**
	 * End of the queue.
	 */
	private Node last;

	/**
	 * @author ekis
	 * 
	 */
	private class Node {
		private Item item;
		private Node next;
		private Node prev;
	}

	/**
	 * Construct an empty deque.
	 */
	public Deque() {
		first = null;
		last = null;
		N = 0;
	}

	/**
	 * Answers whether the deque is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return (N == 0);
	}

	/**
	 * Returns the number of items on the deque.
	 * 
	 * @return
	 */
	public int size() {
		return N;
	}

	/**
	 * Inserts the item at the front.
	 * 
	 * @param item
	 */
	public void addFirst(Item item) {
		validateItem(item);
		
		Node newNode = new Node();
		newNode.item = item;

		if (first == null) {
			first = newNode;
			last = newNode;
		} else {
			Node oldFirst = first;
			first = newNode;
			first.next = oldFirst;
			oldFirst.prev = first;
		}

		N++;
	}
	
	/**
	 * Inserts the item at the end.
	 * 
	 * @param item
	 */
	public void addLast(Item item) {
		validateItem(item);
		
		Node newNode = new Node();
		newNode.item = item;
		
		if (last == null) {
			last = newNode;
			first = newNode;
		} else {
			Node oldLast = last;
			last = newNode;
			last.prev = oldLast;
			oldLast.next = last;
		}

		N++;
	}

	/**
	 * Deletes and returns the item at the front.
	 * 
	 * @return
	 */
	public Item removeFirst() {
		if (first == null) throw new NoSuchElementException(); // empty deque
		Item item = first.item;
		first = first.next;
		if (first != null && first.prev != null) {
			first.prev = null; // remove pointer to element just deleted!
		}
		if (first == null) last = null;
		N--;
		return item;
	}

	/**
	 * Deletes and returns the item at the end.
	 * 
	 * @return
	 */
	public Item removeLast() {
		if (last == null) throw new NoSuchElementException(); // empty deque
		Item item = last.item;
		last = last.prev;
		if (last != null && last.next != null) {
			last.next = null; // remove pointer to element just deleted!
		}
		if (last == null) first = null;
		N--;
		return item;
	}
	
	/*
	 * Return an iterator over items in order from front to end.
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	/**
	 * Iterates over items in deque with order from front to end.
	 * 
	 * @author ekis
	 * @param <Item>
	 */
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove ops not supported");
		}
	}

	/**
	 * @param item
	 */
	private void validateItem(Item item) {
		if (item == null)
			throw new NullPointerException("Null item disallowed");
	}

	/**
	 * Unit test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Deque<String> dq = new Deque<String>();
		String qMirror = "";
		String qReturn = "";
		
		int size = 0;
		StdOut.println("===== START =====");
		
		for (int i = 0; i < 1000; i++) {
			String qd = "";
			for (String s : dq)
				qd += s;
			StdOut.println("Q: " + qd);
			StdOut.println("M: " + qMirror);
			if (!qd.equals(qMirror)) System.out.println("random test failed at " + i);

			if (Math.random() > .7 && !dq.isEmpty()) {
				// empty in 20%
				if (Math.random() > .5) {
					StdOut.print("-F: ");
					String rVal = dq.removeFirst();
					String rMir = qMirror.substring(0, 1);
					StdOut.println(rVal + " " + rMir);
					if (!rMir.equals(rVal)) System.out.println("Should match F");
					//assertEquals(, rMir, rVal);
					qMirror = qMirror.substring(1);
					size--;
				} else {
					StdOut.print("-L: ");
					String rVal = dq.removeLast();
					String rMir = qMirror.substring(qMirror.length() - 1);
					StdOut.println(rVal + " " + rMir);
					if (!rMir.equals(rVal)) System.out.println("Should match L");
					//assertEquals("Should match L", rMir, rVal);
					qMirror = qMirror.substring(0, qMirror.length() - 1);
					size--;
				}
			} else {
				size++;
				String add = "" + (char) (int) (65 + Math.random() * 26);
				// add item
				if (Math.random() > .5) {
					dq.addFirst(add);
					qMirror = add + qMirror;
				} else {
					dq.addLast(add);
					qMirror = qMirror + add;
				}
			}
		}

		String qLeft = "";
		for (String s : dq)
			qLeft += s;
		
		if (size == dq.size()) System.out.println("Queue should report size: " + size);
		else System.out.println("Queue size ->" + size + "; dq size ->" + dq.size());
//		
		//assertEquals("Queue should report size", size, dq.size());
		if (qLeft == qMirror) System.out.println("random test left over: " + qLeft);
		else System.out.println("QLEFT -> " + qLeft + "; QMIRROR -> " + qMirror);
		//assertEquals("random test left over", qLeft, qMirror);	
		
//		Deque<String> dek = new Deque<String>();
//		String test1 = "test1";
//		String test2 = "test2";
//		String test3 = "test3";
//		boolean testb = false;
//
// 		StdOut.println("START TESTS");
//		// size at start
//		StdOut.println("    size at start");
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: Size at start == " + dek.size());
//		}
//		// add remove one front
//		StdOut.println("    add remove one front");
//		dek.addFirst(test1);
//		if (dek.size() != 1) {
//			StdOut.println("FAIL: inserting one, front, size == " + dek.size());
//		}
//		if (dek.removeFirst() != test1) {
//			StdOut.println("FAIL: inserting one, front, remove, not correct item");
//		}
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: inserting one, front, remove, size == "
//					+ dek.size());
//		}
//		// add remove one back
//		StdOut.println("    add remove one back");
//		dek.addLast(test1);
//		if (dek.size() != 1) {
//			StdOut.println("FAIL: inserting one, back, size == " + dek.size());
//		}
//		if (dek.removeLast() != test1) {
//			StdOut.println("FAIL: inserting one, back, remove, not correct item");
//		}
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: inserting one, back, remove, size == "
//					+ dek.size());
//		}
//		// add two front, remove two back
//		StdOut.println("    add two front, remove two back");
//		dek.addFirst(test1);
//		dek.addFirst(test2);
//		if (dek.size() != 2) {
//			StdOut.println("FAIL: add two front, size == " + dek.size());
//		}
//		if (dek.removeLast() != test1) {
//			StdOut.println("FAIL: add two front, remove one back, not correct item");
//		}
//		if (dek.size() != 1) {
//			StdOut.println("FAIL: add two front, remove one back, size == "
//					+ dek.size());
//		}
//		if (dek.removeLast() != test2) {
//			StdOut.println("FAIL: add two front, remove two back, not correct item");
//		}
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: add two front, remove two back, size == "
//					+ dek.size());
//		}
//		// add two back, remove two front
//		StdOut.println("    add two back, remove two front");
//		dek.addLast(test1);
//		dek.addLast(test2);
//		if (dek.size() != 2) {
//			StdOut.println("FAIL: add two back, size == " + dek.size());
//		}
//		if (dek.removeFirst() != test1) {
//			StdOut.println("FAIL: add two back, remove one front, not correct item");
//		}
//		if (dek.size() != 1) {
//			StdOut.println("FAIL: add two back, remove one front, size == "
//					+ dek.size());
//		}
//		if (dek.removeFirst() != test2) {
//			StdOut.println("FAIL: add two back, remove two front, not correct item");
//		}
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: add two back, remove two front, size == "
//					+ dek.size());
//		}
//		// add front, add back, remove two back
//		StdOut.println("    add front, add back, remove two back");
//		dek.addFirst(test1);
//		dek.addLast(test2);
//		if (dek.size() != 2) {
//			StdOut.println("FAIL: add front, add back, size == " + dek.size());
//		}
//		if (dek.removeLast() != test2) {
//			StdOut.println("FAIL: add front, add back, remove one back, not correct item");
//		}
//		if (dek.size() != 1) {
//			StdOut.println("FAIL: add front, add back, remove one back, size == "
//					+ dek.size());
//		}
//		if (dek.removeLast() != test1) {
//			StdOut.println("FAIL: add front, add back, remove two back, not correct item");
//		}
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: add front, add back, remove two back, size == "
//					+ dek.size());
//		}
//		// add back, add front, remove two front
//		StdOut.println("    add back, add front, remove two front");
//		dek.addLast(test1);
//		dek.addFirst(test2);
//		if (dek.size() != 2) {
//			StdOut.println("FAIL: add back, add front, size == " + dek.size());
//		}
//		if (dek.removeFirst() != test2) {
//			StdOut.println("FAIL: add back, add front, remove one front, not correct item");
//		}
//		if (dek.size() != 1) {
//			StdOut.println("FAIL: add back, add front, remove one front, size == "
//					+ dek.size());
//		}
//		if (dek.removeFirst() != test1) {
//			StdOut.println("FAIL: add back, add front, remove two front, not correct item");
//		}
//		if (dek.size() != 0) {
//			StdOut.println("FAIL: add back, add front, remove two front, size == "
//					+ dek.size());
//		}
//		// remove last, empty deque
//		StdOut.println("    remove last, empty deque");
//		testb = false;
//		try {
//			dek.removeLast();
//		} catch (java.util.NoSuchElementException e) {
//			testb = true;
//		}
//		if (!testb) {
//			StdOut.println("FAIL: empty deque, remove back, exception not thrown");
//		}
//		// remove first, empty deque
//		StdOut.println("    remove first, empty deque");
//		testb = false;
//		try {
//			dek.removeFirst();
//		} catch (java.util.NoSuchElementException e) {
//			testb = true;
//		}
//		if (!testb) {
//			StdOut.println("FAIL: empty deque, remove front, exception not thrown");
//		}
//		// add null, front
//		StdOut.println("    add null, front");
//		testb = false;
//		try {
//			dek.addFirst(null);
//		} catch (java.lang.NullPointerException e) {
//			testb = true;
//		}
//		if (!testb) {
//			StdOut.println("FAIL: add null, front, exception not thrown");
//		}
//		// add null, back
//		StdOut.println("    add null, back");
//		testb = false;
//		try {
//			dek.addLast(null);
//		} catch (java.lang.NullPointerException e) {
//			testb = true;
//		}
//		if (!testb) {
//			StdOut.println("FAIL: add null, back, exception not thrown");
//		}
//		// iterator test, order
//		StdOut.println("    iterator test, order of elements");
//		dek.addLast(test2);
//		dek.addLast(test3);
//		dek.addFirst(test1);
//		String temp_iterator = "";
//		for (String s : dek) {
//			temp_iterator = temp_iterator + s;
//		}
//		if (!temp_iterator.equals(test1 + test2 + test3)) {
//			StdOut.println("FAIL: iterator, not correct");
//		}
//		dek = new Deque<String>();
//		// iterator test, remove
//		StdOut.println("    iterator test, remove");
//		testb = false;
//		try {
//			Iterator itr1 = dek.iterator();
//			itr1.remove();
//		} catch (java.lang.UnsupportedOperationException e) {
//			testb = true;
//		}
//		if (!testb) {
//			StdOut.println("FAIL: iterator test, remove, exception not thrown");
//		}
//		dek = new Deque<String>();
//		// iterator test, next, hasNext
//		StdOut.println("    iterator test, next, hasNext");
//		dek.addFirst(test1);
//		dek.addLast(test2);
//		Iterator itr2 = dek.iterator();
//		if (itr2.hasNext() == false) {
//			StdOut.println("FAIL: iterator, two items, hasNext, should have");
//		}
//		if (itr2.next() != test1) {
//			StdOut.println("FAIL: iterator, two items, next, wrong item");
//		}
//		if (itr2.hasNext() == false) {
//			StdOut.println("FAIL: iterator, one item, hasNext, should have");
//		}
//		if (itr2.next() != test2) {
//			StdOut.println("FAIL: iterator, one item, next, wrong item");
//		}
//		if (itr2.hasNext() == true) {
//			StdOut.println("FAIL: iterator, one item, hasNext, shouldn't have");
//		}
//		dek.removeFirst();
//		dek.removeFirst();
//		dek = new Deque<String>();
//		// iterator test, empty next
//		StdOut.println("    iterator test, remove");
//		testb = false;
//		try {
//			Iterator itr3 = dek.iterator();
//			itr3.next();
//		} catch (java.util.NoSuchElementException e) {
//			testb = true;
//		}
//		if (!testb) {
//			StdOut.println("FAIL: iterator test, empty next, exception not thrown");
//		}
//		dek = new Deque<String>();
//		StdOut.println("END TESTS");

	}
}
