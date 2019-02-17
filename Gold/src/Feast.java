import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Feast {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Feast\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("feast.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("feast.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int T = Integer.parseInt(ln.nextToken());
		int A = Integer.parseInt(ln.nextToken());
		int B = Integer.parseInt(ln.nextToken());
		boolean[][] dp = new boolean[2][T + 1];
		dp[0][0] = true;
		// Dp[0][x] is if x fullness can be reached without water dp[1][x] is if x can be reached with water
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < T + 1; j++) {
				if(!dp[i][j]) {
					continue;
				}
				if(j + A <= T) {
					dp[i][j + A] = true;
				}
				if(j + B <= T) {
					dp[i][j + B] = true;
				}
				if(i == 0) {
					dp[i + 1][j / 2] = true;
				}
			}
		}
		int best = 0;
		for (int i = T; i >= 0; i--) {
			if (dp[0][i]) {
				best = i;
				break;
			}
			if (dp[1][i]) {
				best = i;
				break;
			}
		}
		out.println(best);
		out.close();
		in.close();
	}
}