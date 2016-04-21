import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ClosestPair {
	
	private final static String TESTDATA_DIR = "/home/mats/Documents/EDAF05/algdes-labs-master/closest-points/data";
	private final static char SC = File.separatorChar;

	public PointPair closestPairRec(Point[] Px, Point[] Py) {
		if (Px.length <= 3) {
			// find closest pair by measuring all pairwise distances
			return closestPairN2(Px);
		}
		
		// Construct Qx, Qy, Rx, Ry
		int divide = Px.length / 2;
		Point[] Qx = Arrays.copyOfRange(Px, 0, divide);
		Point[] Rx = Arrays.copyOfRange(Px, divide, Px.length);
		Point[] Qy = Arrays.copyOf(Qx, Qx.length);
		Arrays.sort(Qy, new YComparator());
		Point[] Ry = Arrays.copyOf(Rx, Rx.length);
		Arrays.sort(Ry, new YComparator());
		//Point[] Qy = Arrays.copyOfRange(Py, 0, divide);
		//Point[] Ry = Arrays.copyOfRange(Py, divide, Py.length);
		
		PointPair q = closestPairRec(Qx, Qy);
		PointPair r = closestPairRec(Rx, Ry);
		
		double delta = Math.min(Point.distance(q), Point.distance(r));
		double xStar = Qx[Qx.length - 1].getX();
		
		// Construct Sy
		ArrayList<Point> tmp = new ArrayList<Point>();
		for (int i = 0; i < Py.length; i++) {
			if (Math.abs(Py[i].getX() - xStar) < delta) {
				tmp.add(Py[i]);
			}
		}
		Point[] Sy = tmp.toArray(new Point[0]);
		
		int p1 = -1, p2 = -1;
		double minDist = Double.MAX_VALUE;
		for (int i = 0; i < Sy.length; i++) {
			for (int j = i; j < i+15; j++) {
				if (j >= Sy.length) break;
				if (i != j && Point.distance(Sy[i], Sy[j]) < minDist) {
					minDist = Point.distance(Sy[i], Sy[j]);
					p1 = i;
					p2 = j;
				}
			}
		}
		PointPair s = null;
		if (p1 != -1 && p2 != -1) {
			s = new PointPair(Sy[p1], Sy[p2]);
		}
		
		if (s != null && Point.distance(s) < delta) {
			return s;
		} else if (Point.distance(q) < Point.distance(r)) {
			return q;
		} else {
			return r;
		}
	}
	
	public PointPair closestPair(Point[] P) {
		Point[] Px = Arrays.copyOf(P, P.length);
		Arrays.sort(Px, new XComparator());
		Point[] Py = Arrays.copyOf(P, P.length);
		Arrays.sort(Py, new YComparator());
		
		return closestPairRec(Px, Py);
	}
	
	public PointPair closestPairN2(Point[] P) {
		int p1 = -1, p2 = -1;
		double minDist = Double.MAX_VALUE;
		
		for (int i = 0; i < P.length; i++) {
			for (int j = 0; j < P.length; j++) {
				if (i != j && Point.distance(P[i], P[j]) < minDist) {
					minDist = Point.distance(P[i], P[j]);
					p1 = i;
					p2 = j;
				}
			}
		}
		
		return new PointPair(P[p1], P[p2]);
	}
	
	public Point[] readDataFromFile(Scanner in) {
		int n = -1;
		String line = null;
		String[] parts = null;
		while (in.hasNextLine()) {
			line = in.nextLine().trim();
			parts = line.split("\\s+");
			
			if (line.contains("NODE_COORD_SECTION")) break;
			
			if (parts[0].contains("DIMENSION")) {
				n = Integer.parseInt(parts[parts.length - 1]);
			}
		}
		
		Point[] P = new Point[n];
		System.out.print(n + " ");

		int index = 0;
		while (in.hasNextLine()) {
			line = in.nextLine().trim();
			parts = line.split("\\s+");
			if (parts.length != 3) break;
			P[index++] = new Point(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
		}
		
		return P;
	}
	


	public static double roundToSignificantFigures(double num, int n) {
	    if (num == 0) {
	        return 0;
	    }
	
	    final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
	    final int power = n - (int) d;
	
	    final double magnitude = Math.pow(10, power);
	    final long shifted = Math.round(num*magnitude);
	    return shifted/magnitude;
	}

	
	public String fmt(double d)
	{
	    if (d == (long) d) {
	        return String.format("%d",(long)d);
	    } else {
	        return String.format("%s", roundToSignificantFigures(d, 15));
	    }
	}
	
	public ClosestPair(String filename) {
		Scanner in = null;
		try {
			in = new Scanner(new File(TESTDATA_DIR + SC + filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Point[] P = readDataFromFile(in);
		
		System.out.println(fmt(Point.distance(closestPair(P))));
	}
	
	public static void main(String[] args) {
		File dir = new File(TESTDATA_DIR);

		ArrayList<String> files = new ArrayList<String>();
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.toString().endsWith("-tsp.txt")) {
				String s = f.toString();
				s = s.substring(s.lastIndexOf(SC) + 1);
				files.add(s);
			}
		}
		
		Collections.sort(files);
		
		/*PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(out);*/
		
		for (String filename : files) {
			System.out.print("../data/" + filename.substring(0, filename.length()-8) + ".tsp: ");
			//System.out.print(filename + ": ");
			new ClosestPair(filename);
		}
	}

}
