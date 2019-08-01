import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CircleBarn2 {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\CircleBarn2\\2.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\CircleBarn2\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("cbarn2.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cbarn2.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[] cows = new int[N];
		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(in.readLine());
		}
		long ans = Long.MAX_VALUE;
		for (int start = 0; start < N; start++) {
			int[] rotated = new int[N];
			for (int r = start; r < start + N; r++) {
				rotated[r - start] = cows[r % N];
			}
			long[][] dp = new long[K][N];
			// Dp[k][j] is the min distance with k doors used and last barn at j
			for (int i = 1; i < N; i++) {
				dp[0][i] = dp[0][i - 1] + rotated[i] * i;
			}
			for (int k = 1; k < K; k++) {
				for (int j = k + 1; j < N; j++) {
					dp[k][j] = dp[k - 1][j - 1];
					int sum = 0;
					int curr = rotated[j];
					int lastDoor = j - 1;
					while (lastDoor > 0 && sum < dp[k][j]) {
						sum += curr;
						dp[k][j] = Math.min(dp[k][j], dp[k - 1][lastDoor - 1] + sum);
						curr += rotated[lastDoor];
						lastDoor--;
					}
				}
			}
			ans = Math.min(ans, dp[K - 1][N - 1]);
		}
		out.println(ans);
		out.close();
		in.close();
	}
}