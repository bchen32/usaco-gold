import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Nocross {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Nocross\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Nocross\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("nocross.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocross.out")));
		int N = Integer.parseInt(in.readLine());
		int[][] fields = new int[2][N];
		for (int i = 0; i < N; i++) {
			fields[0][i] = Integer.parseInt(in.readLine());
		}
		for (int i = 0; i < N; i++) {
			fields[1][i] = Integer.parseInt(in.readLine());
		}
		long[][] dp = new long[N][N];
		for (int i = 0; i < N; i++) {
			if (Math.abs(fields[0][0] - fields[1][i]) <= 4) {
				dp[0][i] = 1;
			}
			if (Math.abs(fields[0][i] - fields[1][0]) <= 4) {
				dp[i][0] = 1;
			}
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < N; j++) {
				dp[i][j] = Math.max(Math.max(dp[i - 1][j - 1] + (Math.abs(fields[0][i] - fields[1][j]) <= 4 ? 1 : 0), dp[i - 1][j]), dp[i][j - 1]);
			}
		}
		out.println(dp[N - 1][N - 1]);
		out.close();
		in.close();
	}
}