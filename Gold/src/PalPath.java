import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PalPath {
	
	static final int MOD = 1000000007;
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\PalPath\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\PalPath\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("palpath.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palpath.out")));
		int N = Integer.parseInt(in.readLine());
		char[][] grid = new char[N][N];
		for (int i = 0; i < N; i++) {
			grid[i] = in.readLine().toCharArray();
		}
		long[][] dp = new long[N][N];
		for (int i = 0; i < N; i++) {
			dp[i][i] = 1;
		}
		for (int i = N - 1; i >= 1; i--) {
			long[][] next = new long[N][N];
			for (int j = 0; j < N; j++) {
				int rA = j;
				int cA = i - j - 1;
				if (cA < 0) {
					break;
				}
				for (int k = 0; k < N; k++) {
					int rB = k;
					int cB = 2 * N - i - rB - 1;
					if (cB >= N || grid[rA][cA] != grid[rB][cB]) {
						continue;
					}
					next[rA][rB] += dp[rA][rB];
					if (rA + 1 < N) {
						next[rA][rB] += dp[rA + 1][rB];
					}
					if (rB - 1 >= 0) {
						next[rA][rB] += dp[rA][rB - 1];
					}
					if (rA + 1 < N && rB - 1 >= 0) {
						next[rA][rB] += dp[rA + 1][rB - 1];
					}
					next[rA][rB] %= MOD;
				}
			}
			dp = next;
		}
		out.println(dp[0][N - 1]);
		out.close();
		in.close();
	}
}