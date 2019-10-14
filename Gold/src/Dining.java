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

public class Dining {

	static final int INF = 1 << 30;

	int N;
	LinkedList<DEdge>[] adjList;

	public Dining(int N, LinkedList<DEdge>[] adjList) {
		this.N = N;
		this.adjList = adjList;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Dining\\2.in"));
		// BufferedReader in = new BufferedReader(new
		// FileReader("H:\\git\\USACO-Gold\\Gold\\Dining\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("dining.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		LinkedList<DEdge>[] adjList = new LinkedList[N + 1];
		for (int i = 0; i < N + 1; i++) {
			adjList[i] = new LinkedList<DEdge>();
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			int weight = Integer.parseInt(tk.nextToken());
			adjList[a].add(new DEdge(b, weight));
			adjList[b].add(new DEdge(a, weight));
		}
		Dining solve = new Dining(N, adjList);
		int[] before = solve.dijkstraAdjList(N - 1, N);
		for (int i = 0; i < K; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int yum = Integer.parseInt(tk.nextToken());
			adjList[N].add(new DEdge(a, before[a] - yum));
			adjList[a].add(new DEdge(N, before[a] - yum));
		}
		solve.adjList = adjList;
		int[] after = solve.dijkstraAdjList(N, N + 1);
		for (int i = 0; i < N - 1; i++) {
			out.println((after[i] <= before[i]) ? 1 : 0);
		}
		out.close();
		in.close();
	}

	public int[] dijkstraAdjList(int root, int SZ) {
		Queue<DNode> heap = new PriorityQueue<DNode>();
		int[] dist = new int[SZ];
		Arrays.fill(dist, INF);
		boolean[] inSet = new boolean[SZ];
		heap.add(new DNode(root, 0));
		dist[root] = 0;

		while (!heap.isEmpty()) {
			int u = heap.poll().num;
			inSet[u] = true;
			LinkedList<DEdge> adj = adjList[u];
			ListIterator<DEdge> iterate = adj.listIterator();
			while (iterate.hasNext()) {
				DEdge currEdge = iterate.next();
				int v = currEdge.other;
				int distThroughU = dist[u] + currEdge.weight;
				if (!inSet[v]) {
					if (distThroughU < dist[v]) {
						dist[v] = distThroughU;
						DNode toAdd = new DNode(v, distThroughU);
						heap.add(toAdd);
					}
				}
			}
		}
		return dist;
	}
}

class DNode implements Comparable<DNode> {
	int num;
	int dist;

	public DNode(int num, int dist) {
		this.num = num;
		this.dist = dist;
	}

	@Override
	public int compareTo(DNode other) {
		if (this.dist == other.dist) {
			return Integer.compare(this.num, other.num);
		}
		return Integer.compare(this.dist, other.dist);
	}
}

class DEdge {
	int other;
	int weight;

	public DEdge(int o, int w) {
		other = o;
		weight = w;
	}
}