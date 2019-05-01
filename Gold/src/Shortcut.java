import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Shortcut {
	
	static final int INF = 1 << 30;
	
	int N;
	LinkedList<SEdge>[] adjList;
	
	public Shortcut(int N, LinkedList<SEdge>[] adjList) {
		this.N = N;
		this.adjList = adjList;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Shortcut\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Shortcut\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("shortcut.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int T = Integer.parseInt(tk.nextToken());
		LinkedList<SEdge>[] adjList = new LinkedList[M];
		int[] numCows = new int[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			numCows[i] = Integer.parseInt(tk.nextToken());
		}
		for (int i = 0; i < M; i++) {
			adjList[i] = new LinkedList<SEdge>();
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			int w = Integer.parseInt(tk.nextToken());
			adjList[a].add(new SEdge(b, w));
			adjList[b].add(new SEdge(a, w));
		}
		Shortcut solve = new Shortcut(N, adjList);
		int[] before = solve.dijkstraAdjList(0);
		out.close();
		in.close();
	}
	
	public int[] dijkstraAdjList(int root) {
		Queue<SNode> heap = new PriorityQueue<SNode>();
		int[] dist = new int[N];
		Arrays.fill(dist, INF);
		boolean[] inSet = new boolean[N];
		heap.add(new SNode(root, 0));
		dist[root] = 0;
		
		while (!heap.isEmpty()) {
			int u = heap.poll().num;
			inSet[u] = true;
			LinkedList<SEdge> adj = adjList[u];
			ListIterator<SEdge> iterate = adj.listIterator();
			while (iterate.hasNext()) {
				SEdge currEdge = iterate.next();
				int v = currEdge.other;
				int distThroughU = dist[u] + currEdge.weight;
				if (!inSet[v]) {
					if (distThroughU < dist[v]) {
						dist[v] = distThroughU;
						SNode toAdd = new SNode(v, distThroughU);
						heap.add(toAdd);
					}
				}
			}
		}
		return dist;
	}
}

class SNode implements Comparable<SNode> {
	int num;
	int dist;
	
	public SNode(int num, int dist) {
		this.num = num;
		this.dist = dist;
	}
	
	@Override
	public int compareTo(SNode other) {
		// TODO Auto-generated method stub
		if (this.dist == other.dist) {
			return Integer.compare(this.num, other.num);
		}
		return Integer.compare(this.dist, other.dist);
	}
}

class SEdge {
	int other;
	int weight;
	
	public SEdge(int o, int w) {
		other = o;
		weight = w;
	}
}