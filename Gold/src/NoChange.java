import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NoChange {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\NoChange\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("nochange.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nochange.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int K = Integer.parseInt(tk.nextToken());
		int N = Integer.parseInt(tk.nextToken());
		int[] coins = new int[K];
		int[] prices = new int[N];
		for (int i = 0; i < K; i++) {
			coins[i] = Integer.parseInt(in.readLine());
		}
		for (int i = 0; i < N; i++) {
			prices[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(coins);
		int[][] singles = new int[K][N + 1];
		for (int i = 0; i < K; i++) {
			int runningSum = 0;
			int ind = 0;
			while (ind < N) {
				runningSum += prices[ind];
				if (runningSum > coins[i]) {
					runningSum -= prices[ind];
					break;
				}
				ind++;
			}
			for (int j = 0; j < N; j++) {
				singles[i][j] = ind;
				runningSum -= prices[j];
				while (ind < N) {
					runningSum += prices[ind];
					if (runningSum > coins[i]) {
						runningSum -= prices[ind];
						break;
					}
					ind++;
				}
			}
			singles[i][N] = N;
		}
		int[] dp = new int[1 << K];
		for (int i = 0; i < K; i++) {
			dp[1 << i] = singles[i][0];
		}
		for (int i = 1; i < 1 << K; i++) {
			if (dp[i] != 0) {
				continue;
			}
			boolean[] comb = toBool(i, K);
			int furthest = 0;
			for (int j = 0; j < K; j++) {
				if (comb[j]) {
					comb[j] = false;
					int newBinary = toBin(comb);
					furthest = Math.max(furthest, singles[j][dp[newBinary]]);
					comb[j] = true;
				}
			}
			dp[i] = furthest;
		}
		int best = -1;
		for (int i = 1; i < 1 << K; i++) {
			if (dp[i] < N) {
				continue;
			}
			boolean[] comb = toBool(i, K);
			int leftOver = 0;
			for (int j = 0; j < K; j++) {
				if (!comb[j]) {
					leftOver += coins[j];
				}
			}
			best = Math.max(best, leftOver);
		}
		out.println(best);
		out.close();
		in.close();
	}

	static boolean[] toBool(int x, int numDigs) {
		boolean[] b = new boolean[numDigs];
		for (int i = 0; i < numDigs; i++) {
			if ((x & (1 << i)) == 1 << i) {
				b[i] = true;
			}
		}
		return b;
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
}