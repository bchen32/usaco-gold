import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Radio {
	
	static int N;
	static int M;
	
	static int[] dX = {0, 1, 0, -1};
	static int[] dY = {1, 0, -1, 0};
	
	static int[] fPath;
	static int[] cPath;
	
	static long[][] dp;

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Radio\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("radio.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("radio.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		N = Integer.parseInt(ln.nextToken());
		M = Integer.parseInt(ln.nextToken());
		dp = new long[N + 1][M + 1];
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < M + 1; j++) {
				dp[i][j] = -1;
			}
		}
		ln = new StringTokenizer(in.readLine());
		Point farmer = new Point(Integer.parseInt(ln.nextToken()), Integer.parseInt(ln.nextToken()));
		ln = new StringTokenizer(in.readLine());
		Point cow = new Point(Integer.parseInt(ln.nextToken()), Integer.parseInt(ln.nextToken()));
		String l1 = in.readLine();
		fPath = new int[N];
		for (int i = 0; i < N; i++) {
			String s = l1.substring(i, i + 1);
			switch (s) {
			case "N":
				fPath[i] = 0;
				break;
			case "E":
				fPath[i] = 1;
				break;
			case "S":
				fPath[i] = 2;
				break;
			case "W":
				fPath[i] = 3;
				break;
			}
		}
		String l2 = in.readLine();
		cPath = new int[M];
		for (int i = 0; i < M; i++) {
			String s = l2.substring(i, i + 1);
			switch (s) {
			case "N":
				cPath[i] = 0;
				break;
			case "E":
				cPath[i] = 1;
				break;
			case "S":
				cPath[i] = 2;
				break;
			case "W":
				cPath[i] = 3;
				break;
			}
		}
		out.println(calc(0, 0, 0 - (farmer.x - cow.x) * (farmer.x - cow.x) - (farmer.y - cow.y) * (farmer.y - cow.y), farmer, cow));
		out.close();
		in.close();
	}
	
	static long calc(int f, int c, long energy, Point farmer, Point cow) {
		long sq = (farmer.x - cow.x) * (farmer.x - cow.x) + (farmer.y - cow.y) * (farmer.y - cow.y);
		long ans = Long.MAX_VALUE;
		if (f == N && c == M) {
			return energy + sq;
		}
		if (dp[f][c] != -1) {
			return dp[f][c] + energy;
		}
		Point newFarmer = null;
		Point newCow = null;
		if (f < N) {
			newFarmer = new Point(farmer.x + dX[fPath[f]], farmer.y + dY[fPath[f]]);
		}
		if (c < M) {
			newCow = new Point(cow.x + dX[cPath[c]], cow.y + dY[cPath[c]]);
		}
		if (f == N) {
			ans = Math.min(ans, calc(f, c + 1, energy + sq, farmer, newCow));
		} else if (c == M) {
			ans = Math.min(ans, calc(f + 1, c, energy + sq, newFarmer, cow));
		} else {
			ans = Math.min(ans, Math.min(calc(f + 1, c, energy + sq, newFarmer, cow), Math.min(calc(f, c + 1, energy + sq, farmer, newCow), calc(f + 1, c + 1, energy + sq, newFarmer, newCow))));
		}
		dp[f][c] = ans - energy;
		return ans;
	}
	
	static class Point {
		int x;
		int y;
		
		public Point(int a, int b) {
			x = a;
			y = b;
		}
	}
}