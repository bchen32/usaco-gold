import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class VisitFarmer {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\VisitFarmer\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("visitfj.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		int T = Integer.parseInt(ln.nextToken());
		int[][] grass = new int[N][N];
		for (int i = 0; i < N; i++) {
			ln = new StringTokenizer(in.readLine());
			for (int j = 0; j < N; j++) {
				grass[i][j] = Integer.parseInt(ln.nextToken());
			}
		}
		PriorityQueue<State> pq = new PriorityQueue<State>();
		while (!pq.isEmpty()) {
			
		}
		out.close();
		in.close();
	}
	
	static class State implements Comparable<State> {
		int x;
		int y;
		int timeUsed;
		int fieldMod;
		
		public State(int a, int b, int c, int d) {
			x = a;
			y = b;
			timeUsed = c;
			fieldMod = d;
		}

		@Override
		public int compareTo(State other) {
			// TODO Auto-generated method stub
			return Integer.compare(this.timeUsed, other.timeUsed);
		}
	}
}