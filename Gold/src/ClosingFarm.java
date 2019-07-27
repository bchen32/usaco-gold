import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ClosingFarm {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\ClosingFarm\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\ClosingFarm\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("closing.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("closing.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[N];
		int[] order = new int[N];
		for (int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			adjList[a].add(b);
			adjList[b].add(a);
		}
		for (int i = N - 1; i >= 0; i--) {
			order[i] = Integer.parseInt(in.readLine()) - 1;
		}
		DisjointSet ds = new DisjointSet(N);
		boolean[] opened = new boolean[N];
		boolean[] answer = new boolean[N];
		int comp = 0;
		for (int i = 0; i < N; i++) {
			int curr = order[i];
			comp++;
			opened[curr] = true;
			for (int adj : adjList[curr]) {
				if (opened[adj] && !ds.find(adj, curr)) {
					ds.union(adj, curr);
					comp--;
				}
			}
			answer[N - i - 1] = comp <= 1;
		}
		for (int i = 0; i < N; i++) {
			out.println(answer[i] ? "YES" : "NO");
		}
		out.close();
		in.close();
	}
	
	static class DisjointSet {
		int[] arr;
		int[] size;
		int N;
		
		public DisjointSet(int N) {
			this.N = N;
			arr = new int[N];
			size = new int[N];
			Arrays.fill(size, 1);
			for (int i = 0; i < N; i++) {
				arr[i] = i;
			}
		}
		
		public int root(int curr) {
			while (arr[curr] != curr) {
				arr[curr] = arr[arr[curr]];
				curr = arr[curr];
			}
			return curr;
		}
		
		public boolean find(int a, int b) {
			if (root(a) == root(b)) {
				return true;
			}
			return false;
		}
		
		public void union(int a, int b) {
			int aRoot = root(a);
			int bRoot = root(b);
			if (size[aRoot] < size[bRoot]) {
				// Subset B is bigger than subset A, so B should be A's parent
				arr[aRoot] = arr[bRoot];
				size[bRoot] += size[aRoot];
			} else {
				arr[bRoot] = arr[aRoot];
				size[aRoot] += size[bRoot];
			}
		}
	}
}