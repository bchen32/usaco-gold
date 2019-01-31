import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MooCast {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\MooCast\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("moocast.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int[][] input = new int[N][2];
		for (int i = 0; i < N; i++) {
			ln = new StringTokenizer(in.readLine());
			input[i][0] = Integer.parseInt(ln.nextToken());
			input[i][1] = Integer.parseInt(ln.nextToken());
		}
		int numEdges = N * (N - 1) / 2;
		Edge[] edges = new Edge[numEdges];
		int x = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < i; j++) {
				int dist = (int) (Math.pow((input[i][0] - input[j][0]), 2) + Math.pow((input[i][1] - input[j][1]), 2));
				edges[x] = new Edge(i, j, dist);
				x++;
			}
		}
		Arrays.sort(edges);
		DisjointSet set = new DisjointSet(N);
		int best = 0;
		for (int i = 0; i < numEdges; i++) {
			Edge curr = edges[i];
			if (!set.find(curr.v1, curr.v2)) {
				// Not connected
				set.union(curr.v1, curr.v2);
				best = curr.dist;
			}
			if (set.size[curr.v1] == N) {
				break;
			}
		}
		out.println(best);
		out.close();
		in.close();
	}
	
	static class Edge implements Comparable<Edge> {
		int v1;
		int v2;
		int dist;
		
		public Edge(int a, int b, int c) {
			v1 = a;
			v2 = b;
			dist = c;
		}

		@Override
		public int compareTo(Edge other) {
			return Integer.compare(this.dist, other.dist);
		}
	}
	
	static class DisjointSet {
		int[] arr;
		int[] size;
		int N;
		
		public DisjointSet(int a) {
			N = a;
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
		
		public boolean find (int a, int b) {
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