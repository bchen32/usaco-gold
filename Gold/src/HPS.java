import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class HPS {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\HPS\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("hps.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int K = Integer.parseInt(ln.nextToken());

		int[] moves = new int[N];
		int[] beats = { 1, 2, 0 };
		// Beats[i] is the move that beats i
		for (int i = 0; i < N; i++) {
			String nextMove = in.readLine();
			moves[i] = nextMove.equals("H") ? 0 : (nextMove.equals("P") ? 1 : 2);
		}
		int[][][] dp = new int[3][K + 1][N + 1];
		// Dp[a][i][j] is the max points with i changes and j games while Bessie's
		// current move is a
		for (int k = 0; k < K + 1; k++) {
			for (int n = 1; n < N + 1; n++) {
				for (int move = 0; move < 3; move++) {
					int beatFarmer = beats[moves[n - 1]];
					int noSwitch = dp[move][k][n - 1] + ((move) == beatFarmer ? 1 : 0);
					if (k != 0) {
						int switch1 = dp[(move + 1) % 3][k - 1][n - 1] + (((move + 1) % 3) == beatFarmer ? 1 : 0);
						int switch2 = dp[(move + 2) % 3][k - 1][n - 1] + (((move + 2) % 3) == beatFarmer ? 1 : 0);
						dp[move][k][n] = Math.max(switch1, Math.max(switch2, noSwitch));
					} else {
						dp[move][k][n] = noSwitch;
					}
				}
			}
		}
		int best = 0;
		for (int i = 0; i < K + 1; i++) {
			for (int move = 0; move < 3; move++) {
				best = Math.max(best, dp[move][i][N]);
			}
		}
		out.println(best);
		out.close();
		in.close();
	}
}