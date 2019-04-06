import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Checklist {
	
	public CheckCow[] hCheckCows;
	public CheckCow[] gCheckCows;
	public int H;
	public int G;
	public long[][][] dp;
	
	public Checklist(CheckCow[] hCheckCows, CheckCow[] gCheckCows, int H, int G, long[][][] dp) {
		this.hCheckCows = hCheckCows;
		this.gCheckCows = gCheckCows;
		this.H = H;
		this.G = G;
		this.dp = dp;
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bjchen\\git\\USACO-Gold\\Gold\\Checklist\\10.in"));
		BufferedReader in = new BufferedReader(new FileReader("checklist.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int H = Integer.parseInt(ln.nextToken());
		int G = Integer.parseInt(ln.nextToken());
		CheckCow[] hCheckCows = new CheckCow[H];
		CheckCow[] gCheckCows = new CheckCow[G];
		long[][][] dp = new long[H + 1][G + 1][2];
		for (int i = 0; i < H + 1; i++) {
			for (int j = 0; j < G + 1; j++) {
				for (int k = 0; k < 2; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		for (int i = 0; i < H; i++) {
			ln = new StringTokenizer(in.readLine());
			hCheckCows[i] = new CheckCow(Integer.parseInt(ln.nextToken()), Integer.parseInt(ln.nextToken()));
		}
		for (int i = 0; i < G; i++) {
			ln = new StringTokenizer(in.readLine());
			gCheckCows[i] = new CheckCow(Integer.parseInt(ln.nextToken()), Integer.parseInt(ln.nextToken()));
		}
		CheckCow first = hCheckCows[0];
		Checklist solve = new Checklist(hCheckCows, gCheckCows, H, G, dp);
		out.println(Math.min(solve.calc(1, -1, 0, first, true), solve.calc(0, 0, 0, first, false)));
		out.close();
		in.close();
	}
	
	public long calc(int h, int g, long energy, CheckCow prev, boolean isH) {
		CheckCow curr = (isH) ? hCheckCows[h] : gCheckCows[g];
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
}

class CheckCow {
	int x;
	int y;
	
	public CheckCow(int a, int b) {
		x = a;
		y = b;
	}
}