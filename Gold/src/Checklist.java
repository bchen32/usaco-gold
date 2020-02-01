import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Checklist {

	static int H;
	static int G;
	static Cow[] holsteins;
	static Cow[] guernseys;
	static int[][][] dp;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Checklist\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("checklist.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		H = Integer.parseInt(tk.nextToken());
		G = Integer.parseInt(tk.nextToken());
		holsteins = new Cow[H];
		guernseys = new Cow[G];
		dp = new int[H][G][2];
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < G; j++) {
				for (int k = 0; k < 2; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		for (int i = 0; i < H; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			holsteins[i] = new Cow(x, y);
		}
		for (int i = 0; i < G; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			guernseys[i] = new Cow(x, y);
		}
		H--;
		G--;
		out.println(calc(0, -1, true));
		out.close();
		in.close();
	}

	static int calc(int h, int g, boolean holstein) {
		int ans = Integer.MAX_VALUE;
		if (h == H && g == G) {
			return 0;
		} else if (g >= 0 && dp[h][g][holstein ? 0 : 1] != -1) {
			return dp[h][g][holstein ? 0 : 1];
		} else if (h == H - 1 && g != G) {
			if (holstein) {
				// holstein to guernsey
				int energy = energy(holsteins[h], guernseys[g + 1]);
				ans = energy + calc(h, g + 1, false);
			} else {
				// guernsey to guernsey
				int energy = energy(guernseys[g], guernseys[g + 1]);
				ans = energy + calc(h, g + 1, false);
			}
		} else if (g == G) {
			if (holstein) {
				// holstein to holstein
				int energy = energy(holsteins[h], holsteins[h + 1]);
				ans = energy + calc(h + 1, g, true);
			} else {
				// guernsey to holstein
				int energy = energy(guernseys[g], holsteins[h + 1]);
				ans = energy + calc(h + 1, g, true);
			}
		} else if (h != H && g != G && holstein) {
			// holstein to holstein
			int energy = energy(holsteins[h], holsteins[h + 1]);
			ans = Math.min(ans, energy + calc(h + 1, g, true));
			// holstein to guernsey
			energy = energy(holsteins[h], guernseys[g + 1]);
			ans = Math.min(ans, energy + calc(h, g + 1, false));
		} else if (h != H && g != G) {
			// guernsey to holstein
			int energy = energy(guernseys[g], holsteins[h + 1]);
			ans = Math.min(ans, energy + calc(h + 1, g, true));
			// guernsey to guernsey
			energy = energy(guernseys[g], guernseys[g + 1]);
			ans = Math.min(ans, energy + calc(h, g + 1, false));
		}
		if (g >= 0) {
			dp[h][g][holstein ? 0 : 1] = ans;
		}
		return ans;
	}

	static int energy(Cow a, Cow b) {
		return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
	}

	static class Cow {
		int x;
		int y;

		public Cow(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}