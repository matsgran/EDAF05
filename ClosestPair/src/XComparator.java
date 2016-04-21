import java.util.Comparator;

public class XComparator implements Comparator<Point> {

	@Override
	public int compare(Point a, Point b) {
		if (a.getX() < b.getX()) {
			return -1;
		} else if (a.getX() > b.getX()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
