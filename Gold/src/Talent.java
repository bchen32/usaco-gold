import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Talent {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Talent\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("talent.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int W = Integer.parseInt(ln.nextToken());
		int[] values = new int[N];
		int[] weights = new int[N];
		for (int i = 0; i < N; i++) {
			ln = new StringTokenizer(in.readLine());
			weights[i] = Integer.parseInt(ln.nextToken());
			values[i] = Integer.parseInt(ln.nextToken());
		}
		int[][] dp = new int[N + 1][W + 1];
		
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < W + 1; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 0;
				} else if (weights[i] <= W) {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i]] + values[i]);
				} else {
					dp[i][j] = dp[i - 1][j];
				}
			}
		}
		out.close();
		in.close();
	}
}