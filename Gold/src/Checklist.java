import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Checklist {
	
	static Cow[] hCows;
	static Cow[] gCows;
	static int H;
	static int G;
	static HashMap<State, Integer> dp = new HashMap<State, Integer>();

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Checklist\\7.in"));
		BufferedReader in = new BufferedReader(new FileReader("checklist.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		H = Integer.parseInt(ln.nextToken());
		G = Integer.parseInt(ln.nextToken());
		hCows = new Cow[H];
		gCows = new Cow[G];
		
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
	
	static int calc(int h, int g, int energy, Cow prev, boolean isH) {
		State toAdd = new State(h, g, isH);
		Cow curr = (isH) ? hCows[h] : gCows[g];
		int distSq = (prev.x - curr.x) * (prev.x - curr.x) + (prev.y - curr.y) * (prev.y - curr.y);
		energy += distSq;
		if (dp.containsKey(toAdd)) {
			return dp.get(toAdd) + energy;
		}
		if (h == H - 1 && g == G - 1) {
			return energy;
		}
		int ans;
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
		dp.put(toAdd, ans - energy);
		return ans;
	}
	
	static class State {
		int h;
		int g;
		boolean isH;
		
		public State(int a, int b, boolean c) {
			h = a;
			g = b;
			isH = c;
		}
		
		@Override
		public int hashCode() {
			Boolean b = isH;
			int hash = 7;
			hash = 31 * hash + h;
			hash = 31 * hash + g;
			hash = 31 * hash + b.hashCode();
			return hash;
		}
		
		@Override
		public boolean equals(Object o) {
			State other = (State) o;
			return this.h == other.h && this.g == other.g && this.isH == other.isH;
		}
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