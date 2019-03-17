import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Split {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Split\\7.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Split\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("split.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("split.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		Node[] nodes = new Node[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			nodes[i] = new Node(x, y);
		}
		Arrays.sort(nodes);
		Solver ans = new Solver(N, nodes);
		out.println(Math.max(ans.savedX(), ans.savedY()));
		out.close();
		in.close();
	}
}

class Solver {
	private int N;
	private Node[] nodes;
	
	public Solver(int N, Node[] nodes) {
		this.N = N;
		this.nodes = nodes;
	}
	
	public long savedX() {
		TreeMap<Long, Integer> mapL = new TreeMap<Long, Integer>();
		TreeMap<Long, Integer> mapR = new TreeMap<Long, Integer>();
		for (Node curr : nodes) {
			update(mapR, curr.y, 1);
		}
		long maxArea = (nodes[N - 1].x - nodes[0].x) * (mapR.lastKey() - mapR.firstKey());
		long bestArea = maxArea;
		int line = 0;
		while (nodes[line].x < nodes[N - 1].x) {
			int next = line + 1;
			while (next < N && nodes[next].x == nodes[line].x) {
				next++;
			}
			for (int toAdd = line; toAdd < next; toAdd++) {
				update(mapR, nodes[toAdd].y, -1);
				update(mapL, nodes[toAdd].y, 1);
			}
			bestArea = Math.min(bestArea, (nodes[line].x - nodes[0].x) * (mapL.lastKey() - mapL.firstKey()) + (nodes[N - 1].x - nodes[next].x) * (mapR.lastKey() - mapR.firstKey()));
			line = next;
		}
		return maxArea - bestArea;
	}
	
	private void update(TreeMap<Long, Integer> m, long k, int v) {
		if (!m.containsKey(k)) {
			m.put(k, 0);
		}
		int n = m.get(k) + v;
		if (n == 0) {
			m.remove(k);
		} else {
			m.put(k, n);
		}
	}
	
	public long savedY() {
		for (Node curr : nodes) {
			curr.reflect();
		}
		Arrays.sort(nodes);
		return savedX();
	}
}

class Node implements Comparable<Node> {
	public int x;
	public int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void reflect() {
		int temp = x;
		x = y;
		y = temp;
	}
	
	@Override
	public int compareTo(Node arg0) {
		// TODO Auto-generated method stub
		return Integer.compare(this.x, arg0.x);
	}
}