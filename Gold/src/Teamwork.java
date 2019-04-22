import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Teamwork {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Teamwork\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Teamwork\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("teamwork.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[] skills = new int[N];
		for (int i = 0; i < N; i++) {
			skills[i] = Integer.parseInt(in.readLine());
		}
		int[] dp = new int[N];
		// Dp[i] is the best sum for the first i cows
		dp[0] = skills[0];
		for (int i = 1; i < N; i++) {
			int max = skills[i];
			for (int j = i; j >= 0 && i - j + 1 <= K; j--) {
				max = Math.max(max, skills[j]);
				if (j == 0) {
					dp[i] = Math.max(dp[i], (i - j + 1) * max);
				} else {
					dp[i] = Math.max(dp[i], dp[j - 1] + (i - j + 1) * max);
				}
			}
		}
		out.println(dp[N - 1]);
		out.close();
		in.close();
	}
}