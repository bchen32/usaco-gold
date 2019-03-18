import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Dream {
	
	private int N;
	private int M;
	private int[][] grid;
	
	public Dream(int N, int M, int[][] grid) {
		this.N = N;
		this.M = M;
		this.grid = grid;
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
		out.close();
		in.close();
	}
}

class DreamState {
	int r;
	int c;
	int status;
	
	public DreamState(int r, int c, int status) {
		this.r = r;
		this.c = c;
		this.status = status;
	}
}