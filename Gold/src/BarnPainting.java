import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class BarnPainting {

	static long MOD = 1000000007;
	static LinkedList<Integer>[] adjList;
	static int[] colors;
	static long[][] dp;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\BarnPainting\\4.in"));
		BufferedReader in = new BufferedReader(new FileReader("barnpainting.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		colors = new int[N];
		dp = new long[N][3];
		adjList = new LinkedList[N];
		for (int i = 0; i < N; i++) {
			adjList[i] = new LinkedList<Integer>();
			colors[i] = -1;
			for (int j = 0; j < 3; j++) {
				dp[i][j] = -1;
			}
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
			int b = Integer.parseInt(tk.nextToken()) - 1;
			int c = Integer.parseInt(tk.nextToken()) - 1;
			colors[b] = c;
		}
		long ans = 0;
		if (colors[0] == -1) {
			for (int i = 0; i < 3; i++) {
				ans = ((ans % MOD) + (recurse(0, -1, i, -1) % MOD)) % MOD;
			}
		} else {
			ans = ((ans % MOD) + (recurse(0, -1, colors[0], -1) % MOD)) % MOD;
		}
		out.println(ans);
		out.close();
		in.close();
	}

	static long recurse(int barn, int prev, int currColor, int prevColor) {
		if (colors[barn] != -1 && (colors[barn] != currColor || colors[barn] == prevColor)) {
			return 0;
		}
		if (dp[barn][currColor] != -1) {
			return dp[barn][currColor];
		}
		long overall = 1;
		for (int adj : adjList[barn]) {
			if (adj != prev) {
				long sum = 0;
				for (int i = 0; i < 3; i++) {
					if (i != currColor) {
						sum = ((sum % MOD) + (recurse(adj, barn, i, currColor) % MOD)) % MOD;
					}
				}
				overall = ((overall % MOD) * (sum % MOD)) % MOD;
			}
		}
		dp[barn][currColor] = overall;
		return overall;
	}
}