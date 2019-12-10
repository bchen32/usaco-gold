import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class VacationPlanning {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\VacationPlanning\\6.in"));
		BufferedReader in = new BufferedReader(new FileReader("vacationgold.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("vacationgold.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int Q = Integer.parseInt(tk.nextToken());
		int[] hubs = new int[K];
		int[][] distsFrom = new int[K][N];
		int[][] distsTo = new int[K][N];
		LinkedList<Edge>[] adjList = new LinkedList[N];
		LinkedList<Edge>[] adjListFlipped = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<Edge>();
			adjListFlipped[i] = new LinkedList<Edge>();
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			int d = Integer.parseInt(tk.nextToken());
			adjList[a].add(new Edge(b, d));
			adjListFlipped[b].add(new Edge(a, d));
		}
		for (int i = 0; i < K; i++) {
			hubs[i] = Integer.parseInt(in.readLine()) - 1;
		}
		for (int i = 0; i < K; i++) {
			distsFrom[i] = dijkstra(adjList, hubs[i], N);
			distsTo[i] = dijkstra(adjListFlipped, hubs[i], N);
		}
		int numPossible = 0;
		long total = 0;
		for (int i = 0; i < Q; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			long bestDist = Long.MAX_VALUE;
			for (int j = 0; j < K; j++) {
				if (distsTo[j][a] == Integer.MAX_VALUE || distsFrom[j][b] == Integer.MAX_VALUE) {
					continue;
				}
				bestDist = Math.min(bestDist, distsTo[j][a] + distsFrom[j][b]);
			}
			if (bestDist < Long.MAX_VALUE) {
				numPossible++;
				total += bestDist;
			}
		}
		out.println(numPossible + "\n" + total);
		out.close();
		in.close();
	}

	static int[] dijkstra(LinkedList<Edge>[] adjList, int root, int N) {
		PriorityQueue<Node> heap = new PriorityQueue<Node>();
		int[] dists = new int[N];
		Arrays.fill(dists, Integer.MAX_VALUE);
		boolean[] inSet = new boolean[N];
		heap.add(new Node(root, 0));
		dists[root] = 0;

		while (!heap.isEmpty()) {
			int u = heap.poll().num;
			inSet[u] = true;
			LinkedList<Edge> adj = adjList[u];
			for (Edge currEdge : adj) {
				int v = currEdge.other;
				int distsThroughU = dists[u] + currEdge.weight;
				if (!inSet[v]) {
					if (distsThroughU < dists[v]) {
						dists[v] = distsThroughU;
						heap.add(new Node(v, distsThroughU));
					}
				}
			}
		}
		return dists;
	}

	static class Node implements Comparable<Node> {
		int num;
		long dist;

		public Node(int num, long dist) {
			this.num = num;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node other) {
			if (this.dist == other.dist) {
				return Integer.compare(this.num, other.num);
			}
			return Long.compare(this.dist, other.dist);
		}
	}

	static class Edge {
		int other;
		int weight;

		public Edge(int o, int w) {
			other = o;
			weight = w;
		}
	}
}