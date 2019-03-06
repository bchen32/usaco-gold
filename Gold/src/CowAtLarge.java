import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CowAtLarge {
	
	LinkedList<Integer>[] adjList;
	int N;
	int root;
	int[] distRoot;
	int[] distLeaf;
	int[] parents;
	boolean[] visited;
	LinkedList<Integer> leaves = new LinkedList<Integer>();
	
	public CowAtLarge(LinkedList<Integer>[] a, int n, int k) {
		adjList = a;
		N = n;
		root = k;
		distRoot = new int[N];
		distLeaf = new int[N];
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			if (adjList[i].size() == 1) {
				leaves.add(i);
			}
		}
	}
	
	public void root() {
		visited = new boolean[N];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(root);
		while (!q.isEmpty()) {
			int curr = q.remove();
			visited[curr] = true;
			for (int j : adjList[curr]) {
				if (!visited[j]) {
					parents[j] = curr;
					distRoot[j] = distRoot[curr] + 1;
					q.add(j);
				}
			}
		}
	}
	
	public void leaf() {
		Arrays.fill(distLeaf, 1 << 30);
		Queue<Integer> q = new LinkedList<Integer>();
		for (int i : leaves) {
			distLeaf[i] = 0;
			q.add(i);
			visited[i] = true;
		}
		visited = new boolean[N];
		while (!q.isEmpty()) {
			int curr = q.remove();
			visited[curr] = true;
			for (int j : adjList[curr]) {
				distLeaf[j] = Math.min(distLeaf[curr] + 1, distLeaf[j]);
				if (!visited[j]) {
					q.add(j);
				}
			}
		}
	}
	
	public int solve() {
		int farmersNeeded = 0;
		for (int i = 1; i < N; i++) {
			int parent = parents[i];
			if (distRoot[parent] < distLeaf[parent] && distLeaf[i] <= distRoot[i]) {
				farmersNeeded++;
			}
		}
		return farmersNeeded;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\CowAtLarge\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("atlarge.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("atlarge.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken()) - 1;
		LinkedList<Integer>[] adjList = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < N - 1; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			adjList[a].add(b);
			adjList[b].add(a);
		}
		CowAtLarge calc = new CowAtLarge(adjList, N, K);
		calc.root();
		calc.leaf();
		out.println(calc.solve());
		out.close();
		in.close();
	}
}