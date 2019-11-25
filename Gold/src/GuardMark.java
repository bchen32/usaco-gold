import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GuardMark {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\GuardMark\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("guard.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int H = Integer.parseInt(tk.nextToken());
		int[] height = new int[N];
		int[] weight = new int[N];
		int[] strength = new int[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int h = Integer.parseInt(tk.nextToken());
			int w = Integer.parseInt(tk.nextToken());
			int s = Integer.parseInt(tk.nextToken());
			height[i] = h;
			weight[i] = w;
			strength[i] = s;
		}
		int[] dp = new int[1 << N];
		Arrays.fill(dp, -1);
		int best = -1;
		for (int i = 0; i < N; i++) {
			boolean[] comb = new boolean[N];
			comb[i] = true;
			int bin = toBin(comb);
			dp[bin] = strength[i];
			if (height[i] >= H) {
				best = Math.max(best, strength[i]);
			}
		}
		for (int i = 1; i < 1 << N; i++) {
			if (dp[i] != -1) {
				continue;
			}
			boolean[] comb = toBool(i, N);
			int h = 0;
			for (int j = 0; j < N; j++) {
				if (comb[j]) {
					h += height[j];
					comb[j] = false;
					int newComb = toBin(comb);
					comb[j] = true;
					dp[i] = Math.max(dp[i], Math.min(dp[newComb] - weight[j], strength[j]));
				}
			}
			if (h >= H && dp[i] >= 0) {
				best = Math.max(best, dp[i]);
			}
		}
		out.println(best >= 0 ? best : "Mark is too tall");
		out.close();
		in.close();
	}

	static boolean[] toBool(int x, int numDigs) {
		boolean[] arr = new boolean[numDigs];
		for (int i = 0; i < numDigs; i++) {
			if ((x & (1 << i)) == 1 << i) {
				arr[i] = true;
			}
		}
		return arr;
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