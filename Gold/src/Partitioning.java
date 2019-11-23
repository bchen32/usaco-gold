import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Partitioning {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Partitioning\\3.in"));
		BufferedReader in = new BufferedReader(new FileReader("partition.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("partition.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[][] cows = new int[N][N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			for (int j = 0; j < N; j++) {
				cows[i][j] = Integer.parseInt(tk.nextToken());
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int horizontalComb = 0; horizontalComb < (1 << N - 1); horizontalComb++) {
			int numHorizontal = 0;
			int val = horizontalComb;
			while (val > 0) {
				numHorizontal += val & 1;
				val >>= 1;
			}
			int numVert = K - numHorizontal;
			if (numHorizontal > N - 1 || numVert > N - 1 || numVert < 0) {
				continue;
			}
			int[][] cost = new int[N][N];
			for (int c1 = 0; c1 < N; c1++) {
				int[] rowSum = new int[N];
				for (int c2 = c1; c2 < N; c2++) {
					int runningSum = 0;
					for (int i = 0; i < N; i++) {
						rowSum[i] += cows[i][c2];
						runningSum += rowSum[i];
						cost[c1][c2] = Math.max(cost[c1][c2], runningSum);
						if ((horizontalComb & (1 << i)) > 0) {
							runningSum = 0;
						}
					}
				}
			}
			int[][] dp = new int[N + 1][numVert + 2];
			for (int i = 0; i < N + 1; i++) {
				for (int j = 0; j < numVert + 2; j++) {
					dp[i][j] = Integer.MAX_VALUE;
				}
			}
			for (int i = 1; i < N + 1; i++) {
				dp[i][1] = cost[0][i - 1];
			}
			for (int fences = 1; fences < numVert + 2; fences++) {
				for (int i = 1; i < N; i++) {
					for (int col = i + 1; col < N + 1; col++) {
						dp[col][fences] = Math.min(dp[col][fences], Math.max(dp[i][fences - 1], cost[i][col - 1]));
					}
				}
				ans = Math.min(ans, dp[N][fences]);
			}
		}
		out.println(ans);
		out.close();
		in.close();
	}
}