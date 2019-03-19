import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Dream {
	
	private static final int[] dirR = { 0, 0, 1, -1 };
	private static final int[] dirC = { 1, -1, 0, 0 };
	private static final int INF = 1 << 30;
	
	private int N;
	private int M;
	private int[][] grid;
	private int[][][] dists;
	
	public Dream(int N, int M, int[][] grid, int[][][] dists) {
		this.N = N;
		this.M = M;
		this.grid = grid;
		this.dists = dists;
	}
	
	public int solve() {
		
		return 0;
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
		int[][][] dists = new int[N][M][2];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			for (int j = 0; j < M; j++) {
				grid[i][j] = Integer.parseInt(tk.nextToken());
				dists[i][j][0] = INF;
				dists[i][j][1] = INF;
			}
		}
		Dream solver = new Dream(N, M, grid, dists);
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
	int status;
	int dist;
	int dir;
	
	public DreamState(int r, int c, int status, int dist) {
		this.r = r;
		this.c = c;
		this.status = status;
		this.dist = dist;
	}
}