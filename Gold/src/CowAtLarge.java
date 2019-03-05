import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class CowAtLarge {

	LinkedList<Integer>[] adjList;
	int N;
	int root;
	int[] distRoot;
	boolean[] visited;

	public CowAtLarge(LinkedList<Integer>[] a, int n, int k) {
		adjList = a;
		N = n;
		root = k;
		distRoot = new int[N];
	}

	public void DFS() {
		visited = new boolean[N];
		deepen(0, root);
	}

	public void deepen(int height, int vertex) {
		distRoot[vertex] = height;
		visited[vertex] = true;
		for (int v : adjList[vertex]) {
			if (!visited[v]) {
				deepen(height + 1, v);
			}
		}
	}

	public void BFS() {

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\CowAtLarge\\1.in"));
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
		calc.DFS();
		out.close();
		in.close();
	}
}