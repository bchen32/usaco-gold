import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class CowNav {
	int N;
	boolean[][] grid;
	static final int[] rDir = { -1, 0, 1, 0 };
	static final int[] cDir = { 0, 1, 0, -1 };
	
	public CowNav(int N, boolean[][] grid) {
		this.N = N;
		this.grid = grid;
	}
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\CowNav\\3.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\CowNav\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("cownav.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cownav.out")));
		int N = Integer.parseInt(in.readLine());
		boolean[][] grid = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			String ln = in.readLine();
			for (int j = 0; j < N; j++) {
				grid[i][j] = ln.substring(j, j + 1).equals("E") ? true : false;
			}
		}
		CowNav solver = new CowNav(N, grid);
		out.println(solver.bfs());
		out.close();
		in.close();
	}
	
	public int bfs() {
		LinkedList<CNState> q = new LinkedList<CNState>();
		boolean[][][][][] visited = new boolean[N][N][N][N][4];
		
		q.add(new CNState(N - 1, 0, N - 1, 0, 0, 0));
		while (!q.isEmpty()) {
			CNState curr = q.poll();
			int r1 = curr.r1;
			int c1 = curr.c1;
			int r2 = curr.r2;
			int c2 = curr.c2;
			int d1 = curr.d1;
			if (visited[r1][c1][r2][c2][d1]) {
				continue;
			}
			visited[r1][c1][r2][c2][d1] = true;
			if (r1 == 0 && c1 == N - 1 && r2 == 0 && c2 == N - 1) {
				return curr.dist;
			}
			CNState forward = new CNState(r1, c1, r2, c2, d1, curr.dist + 1);
			move(forward);
			CNState turnRight = new CNState(r1, c1, r2, c2, (d1 + 1) % 4, curr.dist + 1);
			CNState turnLeft = new CNState(r1, c1, r2, c2, (d1 + 3) % 4, curr.dist + 1);
			q.add(forward);
			q.add(turnRight);
			q.add(turnLeft);
		}
		return -1;
	}
	
	public void move(CNState forward) {
		int d2 = (forward.d1 + 1) % 4;
		int newR1 = forward.r1 + rDir[forward.d1];
		int newC1 = forward.c1 + cDir[forward.d1];
		int newR2 = forward.r2 + rDir[d2];
		int newC2 = forward.c2 + cDir[d2];
		if (inRange(newR1, newC1) && grid[newR1][newC1] && !(forward.r1 == 0 && forward.c1 == N - 1)) {
			forward.r1 = newR1;
			forward.c1 = newC1;
		}
		if (inRange(newR2, newC2) && grid[newR2][newC2] && !(forward.r2 == 0 && forward.c2 == N - 1)) {
			forward.r2 = newR2;
			forward.c2 = newC2;
		}
	}
	
	public boolean inRange(int newR, int newC) {
		return newR >= 0 && newR < N && newC >= 0 && newC < N;
	}
}

class CNState {
	int r1;
	int c1;
	int r2;
	int c2;
	int d1;
	int dist;
	
	public CNState(int r1, int c1, int r2, int c2, int d1, int dist) {
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
		this.d1 = d1;
		this.dist = dist;
	}
}