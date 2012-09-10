import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Erik
 *
 */
public class PointTest {

    @Test
    public void testCompareTo() {
        // Equal
        assertEquals(0, new Point(0, 1).compareTo(new Point(0, 1)));
        // Compare Y
        assertEquals(-1, new Point(1, 1).compareTo(new Point(1, 2)));
        assertEquals(1, new Point(1, 2).compareTo(new Point(1, 1)));
        // Compare X
        assertEquals(-1, new Point(1, 1).compareTo(new Point(2, 1)));
        assertEquals(1, new Point(2, 1).compareTo(new Point(1, 1)));

    }

    @Test
    public void testSlopeTo() {
        double delta = 0;
        // Vertical Line
        assertEquals(Double.POSITIVE_INFINITY, (new Point(8, 5)).slopeTo(new Point(8, 0)), delta);
        // Line Segment
        assertEquals(Double.NEGATIVE_INFINITY, (new Point(5, 5)).slopeTo(new Point(5, 5)), delta);
        // Horizontal Line
        assertEquals(0, (new Point(0, 5)).slopeTo(new Point(8, 5)), delta);
        assertEquals(0, (new Point(8, 5)).slopeTo(new Point(0, 5)), delta);
        // Negative
        assertEquals(-2, (new Point(1, 2)).slopeTo(new Point(3, -2)), delta);
        // Normal
        assertEquals(2.25, (new Point(1, 1)).slopeTo(new Point(5, 10)), delta);
        assertEquals(6.0, (new Point(1, 1)).slopeTo(new Point(5, 25)), delta);
    }

    @Test
    public void testSlopeOrderComparator() {
        Point originPoint = new Point(1, 1);

        // Same slope (straight line)
        assertEquals(0, originPoint.SLOPE_ORDER.compare(new Point(2, 2), new Point(5, 5)));

        // First slope is smaller
        assertEquals(-1, originPoint.SLOPE_ORDER.compare(new Point(5, 10), new Point(5, 25)));

        // Second slope is smaller
        assertEquals(+1, originPoint.SLOPE_ORDER.compare(new Point(5, 25), new Point(5, 10)));
    }

}
