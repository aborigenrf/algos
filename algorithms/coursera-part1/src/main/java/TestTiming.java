

import edu.princeton.stdlib.StdOut;
import edu.princeton.stdlib.Stopwatch;

public class TestTiming {
	
	public static void main(String[] args) {
		//int seed = Integer.parseInt(args[0]);
		for (int i = 1; i < 30000000; i = 2 * i) {
			Stopwatch watch = new Stopwatch();
			Timing.trial(i, 599003);
			StdOut.println(i + " " + watch.elapsedTime());
		}
	}
}
