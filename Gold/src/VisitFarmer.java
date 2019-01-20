import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class VisitFarmer {
	
	static int[] dirR = {0, 0, 1, -1};
	static int[] dirC = {1, -1, 0, 0};
	static int N;

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\VisitFarmer\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("visitfj.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		N = Integer.parseInt(ln.nextToken());
		int T = Integer.parseInt(ln.nextToken());
		int[][] grass = new int[N][N];
		for (int i = 0; i < N; i++) {
			ln = new StringTokenizer(in.readLine());
			for (int j = 0; j < N; j++) {
				grass[i][j] = Integer.parseInt(ln.nextToken());
			}
		}
		PriorityQueue<State> pq = new PriorityQueue<State>();
		HashMap<State, Integer> map = new HashMap<State, Integer>();
		pq.add(new State(0, 0, 0, 0));
		while (!pq.isEmpty()) {
			boolean better = false;
			State curr = pq.poll();
			if (map.containsKey(curr)) {
				better = (map.get(curr) > curr.timeUsed) ? true : false;
				map.put(curr, Math.min(map.get(curr), curr.timeUsed));
				if (!better) {
					continue;
				}
			}
			map.put(curr, curr.timeUsed);
			for (int i = 0; i < 4; i++) {
				int newR = curr.r + dirR[i];
				int newC = curr.c + dirC[i];
				if (inBounds(newR, newC)) {
					State toAdd = new State(newR, newC, curr.timeUsed + T, (curr.fieldMod + 1) % 3);
					if (toAdd.fieldMod == 0) {
						// Need to eat
						toAdd.timeUsed += grass[toAdd.r][toAdd.c]; 
					}
					if (!map.containsKey(toAdd) || map.get(toAdd) > toAdd.timeUsed) {
						pq.add(toAdd);
					}
				}
			}
		}
		int bestTime = Integer.MAX_VALUE;
		for (int i = 0; i < 3; i++) {
			State possible = new State(N - 1, N - 1, 0, i);
			if (map.containsKey(possible)) {
				bestTime = Math.min(bestTime, map.get(possible));
			}
		}
		out.println(bestTime);
		out.close();
		in.close();
	}
	
	// In range of an matrix
	static boolean inBounds(int r, int c) {
		if (r < N && r >= 0 && c < N && c >= 0) {
			return true;
		}
		return false;
	}
	
	static class State implements Comparable<State> {
		int r;
		int c;
		int timeUsed;
		int fieldMod;
		
		public State(int a, int b, int t, int d) {
			r = a;
			c = b;
			timeUsed = t;
			fieldMod = d;
		}
		
		@Override
		public boolean equals(Object o) {
			State other = (State) o;
			return this.r == other.r && this.c == other.c && this.fieldMod == other.fieldMod;
		}
		
		@Override
		public int hashCode() {
			int hash = 7;
			hash = 31 * hash + r;
			hash = 31 * hash + c;
			hash = 31 * hash + fieldMod;
			return hash;
		}

		@Override
		public int compareTo(State other) {
			// TODO Auto-generated method stub
			return Integer.compare(this.timeUsed, other.timeUsed);
		}
	}
}