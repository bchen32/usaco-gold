import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Cow248 {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\248\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("248.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("248.out")));
		int N = Integer.parseInt(in.readLine());
		int[] nums = new int[N];
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(in.readLine());
		}
		int[][] dp = new int[N][N];
		// Dp[i][j] is the max for subinterval starting at i, with j + 1 length
		int ans = 0;
		for (int l = 0; l < N; l++) {
			for (int i = 0; i + l < N; i++) {
				if (l == 0) {
					dp[i][l] = nums[i];
					continue;
				}
				for (int k = 0; k < l; k++) {
					if (dp[i][k] == dp[i + k + 1][l - k - 1] && dp[i][k] != 0) {
						dp[i][l] = Math.max(dp[i][l], dp[i][k] + 1);
					}
				}
				ans = Math.max(ans, dp[i][l]);
			}
		}
		out.println(ans);
		out.close();
		in.close();
	}
}