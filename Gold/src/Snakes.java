import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Snakes {
	
	public static final int INF = 1 << 30;
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Snakes\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Snakes\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("snakes.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[] snakes = new int[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			snakes[i] = Integer.parseInt(tk.nextToken());
		}
		int[][][] dp = new int[N + 1][K + 1][N];
		// Dp[i][j][k] is min after getting i snakes with j changes and with current net size of snakes[k]
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < K + 1; j++) {
				for (int k = 0; k < N; k++) {
					dp[i][j][k] = INF;
				}
			}
		}
		for (int k = 0; k < K + 1; k++) {
			Arrays.fill(dp[0][k], 0);
		}
		for (int i = 0; i < N; i++) {
			if (snakes[i] < snakes[0]) {
				continue;
			}
			dp[1][0][i] = snakes[i] - snakes[0];
		}
		for (int n = 2; n < N + 1; n++) {
			for (int h = 0; h < N; h++) {
				int height = snakes[h];
				if (height < snakes[n - 1]) {
					continue;
				}
				dp[n][0][h] = dp[n - 1][0][h] + height - snakes[n - 1];
			}
		}
		for (int n = 1; n < N + 1; n++) {
			for (int k = 1; k < K + 1; k++) {
				int bestVal = INF;
				for (int h = 0; h < N; h++) {
					int tryVal = dp[n - 1][k - 1][h];
					bestVal = Math.min(bestVal, tryVal);
				}
				for (int h = 0; h < N; h++) {
					int height = snakes[h];
					if (height < snakes[n - 1]) {
						continue;
					}
					int stay = dp[n - 1][k][h] + snakes[h] - snakes[n - 1];
					int change = bestVal + height - snakes[n - 1];
					if (change <= stay) {
						dp[n][k][h] = change;
					} else {
						dp[n][k][h] = stay;
					}
				}
			}
		}
		int min = INF;
		for (int i = 0; i < N; i++) {
			min = Math.min(min, dp[N][K][i]);
		}
		out.println(min);
		out.close();
		in.close();
	}
}