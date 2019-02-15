import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Checklist {

	static Cow[] hCows;
	static Cow[] gCows;
	static int H;
	static int G;
	static long[][][] dp;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bjchen\\git\\USACO-Gold\\Gold\\Checklist\\10.in"));
		BufferedReader in = new BufferedReader(new FileReader("checklist.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		H = Integer.parseInt(ln.nextToken());
		G = Integer.parseInt(ln.nextToken());
		hCows = new Cow[H];
		gCows = new Cow[G];
		dp = new long[H + 1][G + 1][2];
		for (int i = 0; i < H + 1; i++) {
			for (int j = 0; j < G + 1; j++) {
				for (int k = 0; k < 2; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		for (int i = 0; i < H; i++) {
			ln = new StringTokenizer(in.readLine());
			hCows[i] = new Cow(Integer.parseInt(ln.nextToken()), Integer.parseInt(ln.nextToken()));
		}
		for (int i = 0; i < G; i++) {
			ln = new StringTokenizer(in.readLine());
			gCows[i] = new Cow(Integer.parseInt(ln.nextToken()), Integer.parseInt(ln.nextToken()));
		}
		Cow first = hCows[0];
		out.println(Math.min(calc(1, -1, 0, first, true), calc(0, 0, 0, first, false)));
		out.close();
		in.close();
	}

	static long calc(int h, int g, long energy, Cow prev, boolean isH) {
		Cow curr = (isH) ? hCows[h] : gCows[g];
		long distSq = (prev.x - curr.x) * (prev.x - curr.x) + (prev.y - curr.y) * (prev.y - curr.y);
		energy += distSq;
		if (dp[h + 1][g + 1][((isH) ? 1 : 0)] != -1) {
			return dp[h + 1][g + 1][((isH) ? 1 : 0)] + energy;
		}
		if (h == H - 1 && g == G - 1) {
			return energy;
		}
		long ans;
		if (h == H - 2) {
			if (g != G - 1) {
				ans = calc(h, g + 1, energy, curr, false);
			} else {
				ans = calc(h + 1, g, energy, curr, true);
			}
		} else {
			if (g == G - 1) {
				ans = calc(h + 1, g, energy, curr, true);
			} else {
				ans = Math.min(calc(h + 1, g, energy, curr, true), calc(h, g + 1, energy, curr, false));
			}
		}
		dp[h + 1][g + 1][((isH) ? 1 : 0)] = ans - energy;
		return ans;
	}

	static class Cow {
		int x;
		int y;

		public Cow(int a, int b) {
			x = a;
			y = b;
		}
	}
}