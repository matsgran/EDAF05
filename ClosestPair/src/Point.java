
public class Point {
	
	private double x, y;
	private int index;
	
	public Point(double x, double y, int i) {
		this.x = x;
		this.y = y;
		index = i;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public static double distance(Point p1, Point p2) {
		return Math.sqrt( Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
	
	public static double distance(PointPair p) {
		return distance(p.first, p.second);
	}
	
}
