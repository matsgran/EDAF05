import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class GS {

	public static void stableMatching(Scanner in) {
		int n = 0;
		
		String line = null;
		while ((line = in.nextLine()) != null) {
			if (line.charAt(0) == '#') continue;
			n = Integer.parseInt(line.substring(2));
			break;
		}
		
		String[] manNames = new String[n];
		String[] womanNames = new String[n];
		
		for (int i = 0; i < 2*n; ++i) {
			int pos = 0;
			String name = null;
			if (i % 2 == 0) {
				pos = (in.nextInt()-1)/2;
				name = in.next();
				manNames[pos] = name;
			} else {
				pos = (in.nextInt()-2)/2;
				name = in.next();
				womanNames[pos] = name;
			}
		}
		
		in.nextLine();
		in.nextLine();
		
		int[][] manPref = new int[n][n];
		int[][] womanPref = new int[n][n];
		
		for (int i = 0; i < 2*n; ++i) {
			int pos = 0;
			if (i % 2 == 0) {
				String num = in.next();
				pos = (Integer.parseInt(num.substring(0, num.length()-1)) - 1) / 2;
				for (int j = 0; j < n; ++j) {
					manPref[pos][j] = (in.nextInt() / 2) - 1;
				}
			} else {
				String num = in.next();
				pos = (Integer.parseInt(num.substring(0, num.length()-1)) - 2) / 2;
				for (int j = 0; j < n; ++j) {
					womanPref[pos][j] = ((in.nextInt() + 1) / 2) - 1;
					//womanPref[pos][((in.nextInt() + 1) / 2) - 1] = j;
				}
			}
		}

		LinkedList<Integer> freeMen = new LinkedList<Integer>();
		int[] next = new int[n];	
		int[] current = new int[n];
		int[] currentMan = new int[n];	// only for output
		for (int i = 0; i < n; ++i) {
			freeMen.add(i);
			next[i] = 0;
			current[i] = -1;
			currentMan[i] = -1;
		}
		
		/* --- DEBUG --- */
		/*System.out.println("--- DEBUG ---");
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				System.out.print(manPref[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-------------");*/
		

		while (!freeMen.isEmpty()) {
			int m = freeMen.removeFirst();
			int w = manPref[m][next[m]++];

			if (current[w] == -1) {
				current[w] = m;
				currentMan[m] = w;
			} else {
				int mp = current[w];
				int mPos = 0, mpPos = 0;				
				//mPos = womanPref[w][m];
				//mpPos = womanPref[w][mp];
				for (int i = 0; i < n; ++i) {
					if (womanPref[w][i] == m) mPos = i;
					if (womanPref[w][i] == mp) mpPos = i;
				}
				if (mpPos < mPos) {
					freeMen.addFirst(m);
				} else {
					current[w] = m;
					currentMan[m] = w;
					freeMen.addFirst(mp);
				}
			}
		}
		
		for (int i = 0; i < n; ++i) {
			System.out.println(manNames[i] + " -- " + womanNames[currentMan[i]]);
		}
	}
	
	public static void main(String[] args) {
		Scanner in = null;
		if (args.length == 0) {
			in = new Scanner(System.in);
		} else {
			try {
				in = new Scanner(new File(args[0]));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		stableMatching(in);
	}

}
