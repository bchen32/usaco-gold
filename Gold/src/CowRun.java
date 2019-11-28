import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class CowRun {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CowRun\\7.in"));
		BufferedReader in = new BufferedReader(new FileReader("cowrun.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowrun.out")));
		int N = Integer.parseInt(in.readLine());
		int[] cows = new int[N];
		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(cows);
		int[][][] dp = new int[N][N][2];
		for (int i = 0; i < N; i++) {
			dp[0][i][0] = Math.abs(cows[i]) * N;
			dp[0][i][1] = Math.abs(cows[i]) * N;
		}
		for (int l = 1; l < N; l++) {
			for (int i = 0; i + l < N; i++) {
				int count = N - l;
				int a = dp[l - 1][i + 1][0] + count * (cows[i + 1] - cows[i]);
				int b = dp[l - 1][i + 1][1] + count * (cows[i + l] - cows[i]);
				int c = dp[l - 1][i][0] + count * (cows[i + l] - cows[i]);
				int d = dp[l - 1][i][1] + count * (cows[i + l] - cows[i + l - 1]);
				dp[l][i][0] = Math.min(a, b);
				dp[l][i][1] = Math.min(c, d);
			}
		}
		out.println(Math.min(dp[N - 1][0][0], dp[N - 1][0][1]));
		out.close();
		in.close();
	}
}