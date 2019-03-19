import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Dream {
	
	private static final int[] dirR = { 0, 0, 1, -1 };
	private static final int[] dirC = { 1, -1, 0, 0 };
	private static final int INF = 1 << 30;
	
	private int N;
	private int M;
	private int[][] grid;
	
	public Dream(int N, int M, int[][] grid) {
		this.N = N;
		this.M = M;
		this.grid = grid;
	}
	
	public int solve() {
		int[][][] dp = new int[N][M][2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				dp[i][j][0] = INF;
				dp[i][j][1] = INF;
			}
		}
		LinkedList<DreamState> q = new LinkedList<DreamState>();
		while (!q.isEmpty()) {
			DreamState curr = q.poll();
			for (int i = 0; i < 4; i++) {
				int newR = curr.r + dirR[i];
				int newC = curr.c + dirC[i];
				if (inRange(newR, newC)) {
					int newTile = grid[newR][newC];
					if (newTile != 0 && (newTile != 3 || curr.smell)) {
						// In bounds and passable
						boolean smell = (newTile == 2) ? true : (newTile == 4) ? false : curr.smell;
						if (newTile == 4) {
							// Slide
							// Treat slide as a single move but with different dist
							
						} else if (curr.dist + 1 < dp[newR][newC][smell ? 1 : 0]) {
							dp[newR][newC][smell ? 1 : 0] = curr.dist = 1;
							q.add(new DreamState(newR, newC, smell, curr.dist + 1));
						}
					}
				}
			}
		}
		return Math.min(dp[N - 1][M - 1][0], dp[N - 1][M - 1][1]);
	}
	
	public boolean inRange(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Dream\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Dream\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("dream.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dream.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int[][] grid = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			for (int j = 0; j < M; j++) {
				grid[i][j] = Integer.parseInt(tk.nextToken());
			}
		}
		Dream solver = new Dream(N, M, grid);
		int ans = solver.solve();
		if (ans == Integer.MAX_VALUE) {
			ans = -1;
		}
		out.println(ans);
		out.close();
		in.close();
	}
}

class DreamState {
	int r;
	int c;
	boolean smell;
	int dist;
	
	public DreamState(int r, int c, boolean smell, int dist) {
		this.r = r;
		this.c = c;
		this.smell = smell;
		this.dist = dist;
	}
}