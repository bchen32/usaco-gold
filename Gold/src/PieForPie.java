import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class PieForPie {

	int N;
	int D;
	Pie[] bessie = new Pie[N];
	Pie[] elsie = new Pie[N];
	int[] t;
	int[] d;
	boolean[] visitedBessie;
	boolean[] visitedElsie;

	public PieForPie(int N, int D, Pie[] bessie, Pie[] elsie) {
		this.N = N;
		this.D = D;
		this.bessie = bessie;
		this.elsie = elsie;
		t = new int[N];
		d = new int[N];
		visitedBessie = new boolean[N];
		visitedElsie = new boolean[N];
	}

	public int[] bfs() {
		LinkedList<Pie> q = new LinkedList<Pie>();
		for (int i = 0; i < N; i++) {
			Pie curr = bessie[i];
			if (curr.otherScore == 0) {
				q.add(curr);
				t[curr.num] = 1;
				visitedBessie[curr.num] = true;
			}
		}
		for (int i = 0; i < N; i++) {
			Pie curr = elsie[i];
			if (curr.otherScore == 0) {
				q.add(curr);
				d[curr.num] = 1;
				visitedElsie[curr.num] = true;
			}
		}

		while (!q.isEmpty()) {
			Pie curr = q.poll();
			if (curr.bessie) {
				int found = lowerBound(elsie, curr.score);
				while (found >= 0 && elsie[found].otherScore >= curr.score - D) {
					Pie other = elsie[found];
					if (!visitedElsie[other.num]) {
						visitedElsie[other.num] = true;
						d[other.num] = t[curr.num] + 1;
						q.add(other);
					}
					found--;
				}
			} else {
				int found = lowerBound(bessie, curr.score);
				while (found >= 0 && bessie[found].otherScore >= curr.score - D) {
					Pie other = bessie[found];
					if (!visitedBessie[other.num]) {
						visitedBessie[other.num] = true;
						t[other.num] = d[curr.num] + 1;
						q.add(other);
					}
					found--;
				}
			}
		}
		return t;
	}

	public int lowerBound(Pie[] pies, int find) {
		int min = 0;
		int max = N;
		int mid = -1;
		boolean found = false;

		while (min < max) {
			mid = (min + max) / 2;
			Pie toTry = pies[mid];
			if (toTry.otherScore == find) {
				found = true;
				break;
			} else if (toTry.otherScore < find) {
				min = mid + 1;
			} else {
				max = mid;
			}
		}
		if (found) {
			// Check for duplicates
			int i;
			for (i = mid; i < max; i++) {
				if (pies[i].otherScore > find) {
					break;
				}
			}
			return i - 1;
		}
		return Math.min(N - 1, min - 1);
	}

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\PieForPie\\2.in"));
		// BufferedReader in = new BufferedReader(new
		// FileReader("H:\\git\\USACO-Gold\\Gold\\PieForPie\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("piepie.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int D = Integer.parseInt(tk.nextToken());
		Pie[] bessie = new Pie[N];
		Pie[] elsie = new Pie[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int score = Integer.parseInt(tk.nextToken());
			int otherScore = Integer.parseInt(tk.nextToken());
			bessie[i] = new Pie(score, otherScore, i, true);
		}
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int score = Integer.parseInt(tk.nextToken());
			int otherScore = Integer.parseInt(tk.nextToken());
			elsie[i] = new Pie(otherScore, score, i, false);
		}
		Arrays.sort(bessie);
		Arrays.sort(elsie);
		PieForPie solver = new PieForPie(N, D, bessie, elsie);
		int[] dists = solver.bfs();
		for (int i = 0; i < N; i++) {
			out.println(dists[i] == 0 ? -1 : dists[i]);
		}
		out.close();
		in.close();
	}
}

class Pie implements Comparable<Pie> {
	int score;
	int otherScore;
	int num;
	boolean bessie;

	public Pie(int score, int otherScore, int num, boolean bessie) {
		this.score = score;
		this.otherScore = otherScore;
		this.num = num;
		this.bessie = bessie;
	}

	@Override
	public int compareTo(Pie other) {
		return Integer.compare(this.otherScore, other.otherScore);
	}
}