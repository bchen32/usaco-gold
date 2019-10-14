import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MooTube {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\MooTube\\1.in"));
		// BufferedReader in = new BufferedReader(new
		// FileReader("H:\\git\\USACO-Gold\\Gold\\MooTube\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("mootube.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int Q = Integer.parseInt(tk.nextToken());
		MTEdge[] edges = new MTEdge[N - 1];
		MTQuery[] queries = new MTQuery[Q];
		for (int i = 0; i < N - 1; i++) {
			tk = new StringTokenizer(in.readLine());
			int v1 = Integer.parseInt(tk.nextToken()) - 1;
			int v2 = Integer.parseInt(tk.nextToken()) - 1;
			int w = Integer.parseInt(tk.nextToken());
			edges[i] = new MTEdge(v1, v2, w);
		}
		for (int i = 0; i < Q; i++) {
			tk = new StringTokenizer(in.readLine());
			int threshold = Integer.parseInt(tk.nextToken());
			int start = Integer.parseInt(tk.nextToken()) - 1;
			queries[i] = new MTQuery(threshold, start, i);
		}
		Arrays.sort(edges);
		Arrays.sort(queries);
		int ind = 0;
		MTDisjointSet set = new MTDisjointSet(N);
		int[] numVids = new int[Q];
		for (int i = 0; i < Q; i++) {
			MTQuery curr = queries[i];
			while (ind < N - 1 && edges[ind].w >= curr.threshold) {
				set.union(edges[ind].v1, edges[ind].v2);
				ind++;
			}
			numVids[curr.num] = set.size[set.root(curr.start)] - 1;
		}
		for (int i = 0; i < Q; i++) {
			out.println(numVids[i]);
		}
		out.close();
		in.close();
	}
}

class MTEdge implements Comparable<MTEdge> {
	public int v1, v2, w;

	public MTEdge(int v1, int v2, int w) {
		this.v1 = v1;
		this.v2 = v2;
		this.w = w;
	}

	@Override
	public int compareTo(MTEdge o) {
		return Integer.compare(o.w, w);
	}
}

class MTQuery implements Comparable<MTQuery> {
	public int threshold, start, num;

	public MTQuery(int threshold, int start, int num) {
		this.threshold = threshold;
		this.start = start;
		this.num = num;
	}

	@Override
	public int compareTo(MTQuery o) {
		return Integer.compare(o.threshold, threshold);
	}
}

class MTDisjointSet {
	int[] arr;
	int[] size;
	int N;

	public MTDisjointSet(int N) {
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