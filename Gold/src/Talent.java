import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Talent {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Talent\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("talent.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int W = Integer.parseInt(ln.nextToken());
		int[] values = new int[N];
		int[] weights = new int[N];
		int maxTalent = 0;
		for (int i = 0; i < N; i++) {
			ln = new StringTokenizer(in.readLine());
			weights[i] = Integer.parseInt(ln.nextToken());
			values[i] = Integer.parseInt(ln.nextToken());
			maxTalent += values[i];
		}
		int[] dp = new int[maxTalent + 1];
		// Dp[i] is the lowest weight for i talent
		Arrays.fill(dp, 1 << 30);
		dp[0] = 0;
		for (int i = 0; i < N; i++) {
			for (int j = maxTalent; j > values[i] - 1; j--) {
				dp[j] = Math.min(dp[j], dp[j - values[i]] + weights[i]);
			}
		}
		int best = 0;
		for (int i = 1; i < maxTalent + 1; i++) {
			best = (dp[i] >= W) ? Math.max(best, 1000 * i / dp[i]) : best;
		}
		out.println(best);
		out.close();
		in.close();
	}
}