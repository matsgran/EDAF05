import java.util.HashMap;
import java.util.Map;

public class WordLadders {

	public static void main(String[] args) {
		int n = 4;
		
		/*String[] nodeNames = new String[n];
		nodeNames[0] = "other";
		nodeNames[1] = "there";
		nodeNames[2] = "their";
		nodeNames[3] = "write";*/
		Map<String, Integer> nodeNames = new HashMap<String, Integer>(n);
		nodeNames.put("other", 0);
		nodeNames.put("there", 1);
		nodeNames.put("their", 2);
		nodeNames.put("write", 3);
		
		Graph graph = new Graph(n);
		
		System.out.println(graph.bfs(nodeNames.get("other"), nodeNames.get("their")));
	}

}
