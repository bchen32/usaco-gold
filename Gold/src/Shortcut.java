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
	int T;
	int[] numCows;
	LinkedList<Integer>[] parents;
	LinkedList<Integer>[] travelRecords;
	LinkedList<SEdge>[] adjList;
	
	public Shortcut(int N, int T, int[] numCows, LinkedList<SEdge>[] adjList) {
		this.N = N;
		this.T = T;
		this.numCows = numCows;
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
		Shortcut solver = new Shortcut(N, T, numCows, adjList);
		out.println(solver.solve());
		out.close();
		in.close();
	}
	
	@SuppressWarnings("unchecked")
	public int solve() {
		int[] distBefore = dijkstra(0);
		travelRecords = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			travelRecords[i] = new LinkedList<Integer>();
		}
		for (int i = 1; i < N; i++) {
			LinkedList<Integer> tryPath = new LinkedList<Integer>();
			tryPath.add(i);
			LinkedList<Integer> path = calcRecords(i, tryPath);
			for (int j : path) {
				travelRecords[j].add(i);
			}
		}
		int bestSaved = 0;
		for (int i = 1; i < N; i++) {
			int saved = 0;
			for (int j : travelRecords[i]) {
				int distToI = distBefore[i];
				int saveSingle = distToI - T;
				saved += saveSingle * numCows[j];
			}
			bestSaved = Math.max(bestSaved, saved);
		}
		return bestSaved;
	}
	
	public LinkedList<Integer> calcRecords(int curr, LinkedList<Integer> path) {
		if (curr == 0) {
			return path;
		}
		LinkedList<Integer> best = null;
		for (int i : parents[curr]) {
			LinkedList<Integer> tryPath = new LinkedList<Integer>();
			tryPath.addAll(path);
			tryPath.add(i);
			best = compare(best, calcRecords(i, tryPath));
		}
		return best;
	}
	
	public LinkedList<Integer> compare(LinkedList<Integer> a, LinkedList<Integer> b) {
		if (a == null) {
			return b;
		}
		ListIterator<Integer> iterateA = a.listIterator();
		ListIterator<Integer> iterateB = b.listIterator();
		while (iterateA.hasNext() && iterateB.hasNext()) {
			int aNum = iterateA.next();
			int bNum = iterateB.next();
			if (aNum < bNum) {
				return a;
			}
			if (bNum < aNum) {
				return b;
			}
		}
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public int[] dijkstra(int root) {
		Queue<SNode> heap = new PriorityQueue<SNode>();
		int[] dist = new int[N];
		Arrays.fill(dist, INF);
		parents = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			parents[i] = new LinkedList<Integer>();
		}
		boolean[] inSet = new boolean[N];
		heap.add(new SNode(root, 0));
		dist[root] = 0;
		parents[root].add(-1);
		
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
						parents[v].clear();
						parents[v].add(u);
						SNode toAdd = new SNode(v, distThroughU);
						heap.add(toAdd);
					} else if (distThroughU == dist[v]) {
						dist[v] = distThroughU;
						parents[v].add(u);
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