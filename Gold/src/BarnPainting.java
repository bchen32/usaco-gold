import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BarnPainting {
	
	int N;
	int K;
	int[] colors;
	LinkedList<Integer>[] adjList;
	long[][] dp;
	
	static final long MOD = 1000000007L;
	
	public BarnPainting(int N, int K, int[] colors, LinkedList<Integer>[] adjList) {
		this.N = N;
		this.K = K;
		this.colors = colors;
		this.adjList = adjList;
		dp = new long[N][3];
		for (int i = 0; i < N; i++) {
			Arrays.fill(dp[i], -1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\BarnPainting\\4.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\BarnPainting\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("barnpainting.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[N];
		int[] colors = new int[N];
		Arrays.fill(colors, -1);
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
		for (int i = 0; i < K; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int color = Integer.parseInt(tk.nextToken()) - 1;
			colors[a] = color;
		}
		BarnPainting solver = new BarnPainting(N, K, colors, adjList);
		long ans = 0;
		for (int i = 0; i < 3; i++) {
			ans += solver.recurse(0, i, -1, -1);
			ans %= MOD;
		}
		out.println(ans);
		out.close();
		in.close();
	}
	
	public long recurse(int root, int color, int parent, int prevColor) {
		if (colors[root] != -1 && (colors[root] != color || colors[root] == prevColor)) {
			return 0L;
		}
		if (dp[root][color] != -1) {
			return dp[root][color];
		}
		long ans = 1;
		for (int curr : adjList[root]) {
			if (curr == parent) {
				continue;
			}
			long sub = 0;
			for (int c = 0; c < 3; c++) {
				if (c == color) {
					continue;
				}
				sub += recurse(curr, c, root, color) % MOD;
			}
			ans = (ans * sub) % MOD;
		}
		dp[root][color] = ans;
		return ans;
	}
}