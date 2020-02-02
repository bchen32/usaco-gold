import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class Time {

	static int N;
	static int M;
	static int C;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Time\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("time.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("time.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		N = Integer.parseInt(tk.nextToken());
		M = Integer.parseInt(tk.nextToken());
		C = Integer.parseInt(tk.nextToken());
		LinkedList<Integer>[] reversed = new LinkedList[N];
		int[] moonies = new int[N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			moonies[i] = Integer.parseInt(tk.nextToken());
			reversed[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < M; i++) {
			tk = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(tk.nextToken()) - 1;
			int b = Integer.parseInt(tk.nextToken()) - 1;
			reversed[b].add(a);
		}
		int[][] dp = new int[1001][N];
		for (int i = 0; i < 1001; i++) {
			for (int j = 0; j < N; j++) {
				dp[i][j] = -1 << 30;
			}
		}
		dp[0][0] = 0;
		for (int i = 1; i < 1001; i++) {
			for (int j = 0; j < N; j++) {
				for (int adj : reversed[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][adj] + moonies[j]);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < 1001; i++) {
			ans = Math.max(ans, dp[i][0] - i * i * C);
		}
		out.println(ans);
		out.close();
		in.close();
	}
}