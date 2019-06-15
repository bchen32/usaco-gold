import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Taming {
	
	static final int INF = 1 << 30;
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Taming\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Taming\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("taming.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		tk = new StringTokenizer(in.readLine());
		int[] input = new int[N];
		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(tk.nextToken());
		}
		int[][][] dp = new int[N][N][N + 1];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Arrays.fill(dp[i][j], INF);
			}
		}
		dp[0][0][1] = (input[0] == 0) ? 0 : 1;
		// dp[i][j][k] is the number of altered logs with current position i, last breakout j, and k total breakouts
		for (int i = 1; i < N; i++) {
			for (int j = 0; j <= i; j++) {
				for (int k = 1; k <= i + 1; k++) {
					if (j < i) {
						// no new breakouts
						dp[i][j][k] = dp[i - 1][j][k];
					} else {
						for (int x = 0; x < i; x++) {
							dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][x][k - 1]);
						}
					}
					if (i - j != input[i]) {
						// newly added day doesn't match the log
						dp[i][j][k]++;
					}
				}
			}
		}
		for (int k = 1; k <= N; k++) {
			int best = INF;
			for (int j = 0; j < N; j++) {
				best = Math.min(best, dp[N - 1][j][k]);
			}
			out.println(best);
		}
		out.close();
		in.close();
	}
}