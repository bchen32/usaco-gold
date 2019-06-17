import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Snowboots {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Snowboots\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Snowboots\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("snowboots.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int B = Integer.parseInt(tk.nextToken());
		TreeSet<SnowTile> tilesD = new TreeSet<SnowTile>();
		TreeSet<SnowTile> tilesI = new TreeSet<SnowTile>();
		SnowBoot[] boots = new SnowBoot[B];
		tk = new StringTokenizer(in.readLine());
		int[] ans = new int[B];
		for (int i = 0; i < N; i++) {
			int d = Integer.parseInt(tk.nextToken());
			SnowTile toAdd = new SnowTile(d, i);
			tilesD.add(toAdd);
			toAdd.sortInd = true;
			tilesI.add(toAdd);
		}
		for (int i = 0; i < B; i++) {
			tk = new StringTokenizer(in.readLine());
			boots[i] = new SnowBoot(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()), i);
		}
		Arrays.sort(boots);
		int biggestGap = 0;
		for (int i = 0; i < B; i++) {
			SnowBoot curr = boots[i];
			while (tilesD.first().depth > curr.depth) {
				SnowTile toRemove = tilesD.pollFirst();
				tilesI.remove(toRemove);
				int gap = tilesI.ceiling(toRemove).ind - tilesI.floor(toRemove).ind;
				biggestGap = Math.max(biggestGap, gap);
			}
			if (curr.dist >= biggestGap) {
				ans[curr.ind] = 1;
			}
		}
		for (int i = 0; i < B; i++) {
			out.println(ans[i]);
		}
		out.close();
		in.close();
	}
}

class SnowTile implements Comparable<SnowTile> {
	int depth;
	int ind;
	boolean sortInd = false;
	
	public SnowTile(int d, int i) {
		depth = d;
		ind = i;
	}
	
	@Override
	public int compareTo(SnowTile o) {
		// TODO Auto-generated method stub
		if (!sortInd) {
			if (this.depth == o.depth) {
				return Integer.compare(this.ind, o.ind);
			}
			return Integer.compare(o.depth, this.depth);
		} else {
			return Integer.compare(this.ind, o.ind);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		SnowTile o = (SnowTile) other;
		if (this.depth == o.depth && this.ind == o.ind) {
			return true;
		}
		return false;
	}
}

class SnowBoot implements Comparable<SnowBoot> {
	int depth;
	int dist;
	int ind;
	
	public SnowBoot(int depth, int dist, int i) {
		this.depth = depth;
		this.dist = dist;
		ind = i;
	}
	
	@Override
	public int compareTo(SnowBoot o) {
		// TODO Auto-generated method stub
		if (this.depth == o.depth) {
			return Integer.compare(o.dist, this.dist);
		}
		return Integer.compare(o.depth, this.depth);
	}
}