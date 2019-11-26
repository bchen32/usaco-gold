import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Decathlon {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Decathlon\\3.in"));
		BufferedReader in = new BufferedReader(new FileReader("dec.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dec.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int B = Integer.parseInt(tk.nextToken());
		int[][] cows = new int[N][N];
		@SuppressWarnings("unchecked")
		ArrayList<Bonus>[] bonuses = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			bonuses[i] = new ArrayList<Bonus>();
		}
		for (int i = 0; i < B; i++) {
			tk = new StringTokenizer(in.readLine());
			int event = Integer.parseInt(tk.nextToken()) - 1;
			int req = Integer.parseInt(tk.nextToken());
			int award = Integer.parseInt(tk.nextToken());
			bonuses[event].add(new Bonus(req, award));
		}
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			for (int j = 0; j < N; j++) {
				cows[i][j] = Integer.parseInt(tk.nextToken());
			}
		}
		int[] dp = new int[1 << N];
		for (int i = 0; i < N; i++) {
			boolean[] bool = new boolean[N];
			bool[i] = true;
			int bonusPoints = 0;
			for (Bonus b : bonuses[0]) {
				if (b.pointReq <= cows[i][0]) {
					bonusPoints += b.award;
				}
			}
			dp[toBin(bool)] = cows[i][0] + bonusPoints;
		}
		for (int i = 1; i < 1 << N; i++) {
			if (dp[i] != 0) {
				continue;
			}
			int val = i;
			int eventNum = numSet(val) - 1;
			for (int j = 0; j < N; j++) {
				if ((i & (1 << j)) > 0) {
					val &= ~(1 << j);
					int scoreToTry = dp[val] + cows[j][eventNum];
					val |= (1 << j);
					int bonusPoints = 0;
					for (Bonus b : bonuses[eventNum]) {
						if (b.pointReq <= scoreToTry) {
							bonusPoints += b.award;
						}
					}
					scoreToTry += bonusPoints;
					dp[i] = Math.max(dp[i], scoreToTry);
				}
			}
		}
		out.println(dp[(1 << N) - 1]);
		out.close();
		in.close();
	}

	static int numSet(int bin) {
		int count = 0;
		while (bin > 0) {
			count += bin & 1;
			bin >>= 1;
		}
		return count;
	}

	static int toBin(boolean[] arr) {
		int binary = 0;
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i]) {
				binary |= (1 << 0);
			}
			if (i != 0) {
				binary <<= 1;
			}
		}
		return binary;
	}

	static class Bonus {
		int pointReq;
		int award;

		public Bonus(int pointReq, int award) {
			this.pointReq = pointReq;
			this.award = award;
		}
	}
}