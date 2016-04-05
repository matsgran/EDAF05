import java.util.ArrayList;
import java.util.List;

public class Graph {
	private List<List<Integer>> nodes;
	private int size;
	
	public Graph(int size) {
		this.size = size;
		nodes = new ArrayList<List<Integer>>();
		for (int i = 0 ; i < size; ++i) {
			nodes.add(new ArrayList<Integer>());
		}
		
		nodes.get(0).add(1);
		nodes.get(0).add(2);
		nodes.get(2).add(3);
		nodes.get(3).add(2);
	}
	
	public ArrayList<Integer> getNeighbours(int node) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer i : nodes.get(node)) {
			ret.add(i);
		}
		return ret;
	}
	
	public int bfs(int start, int end) {
		boolean[] discovered = new boolean[size];
		int i = 0;
		List<List<Integer>> L = new ArrayList<List<Integer>>();

		L.add(new ArrayList<Integer>());
		L.get(0).add(start);
		discovered[start] = true;
		
		while (!(L.get(i).isEmpty())) {
			L.add(new ArrayList<Integer>());
			for (Integer u : L.get(i)) {
				ArrayList<Integer> neighbours = getNeighbours(u);
				for (Integer v : neighbours) {
					if (!discovered[v]) {
						if (v == end) return i+1;
						discovered[v] = true;
						L.get(i + 1).add(v);
					}
				}
			}
			++i;
		}
		return -1;
	}
}
