import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Trapped {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Trapped\\3.in"));
		BufferedReader in = new BufferedReader(new FileReader("trapped.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("trapped.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		Haybale[] haybales = new Haybale[N];
		Haybale[] sizeSorted = new Haybale[N];
		int[][] trapped = new int[N][2];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int size = Integer.parseInt(tk.nextToken());
			int pos = Integer.parseInt(tk.nextToken());
			haybales[i] = new Haybale(size, pos);
		}
		Arrays.sort(haybales, new Pos());
		for (int i = 0; i < N; i++) {
			sizeSorted[i] = haybales[i];
			sizeSorted[i].ind = i;
		}
		Arrays.sort(sizeSorted, new Size());
		TreeSet<Integer> placed = new TreeSet<Integer>();
		for (int i = 0; i < N; i++) {
			Haybale curr = sizeSorted[i];
			if (placed.higher(curr.ind) != null) {
				Haybale right = haybales[placed.higher(curr.ind)];
				if (right.pos - curr.pos <= curr.size && right.pos - curr.pos <= right.size) {
					if (trapped[curr.ind][1] == 0 && trapped[right.ind][0] == 0) {
						// mark as trapped
						trapped[curr.ind][1] = 1;
						trapped[right.ind][0] = 2;
					}
				}
			}
			if (placed.lower(curr.ind) != null) {
				Haybale left = haybales[placed.lower(curr.ind)];
				if (curr.pos - left.pos <= curr.size && curr.pos - left.pos <= left.size) {
					if (trapped[left.ind][1] == 0 && trapped[curr.ind][0] == 0) {
						// mark as trapped
						trapped[left.ind][1] = 1;
						trapped[curr.ind][0] = 2;
					}
				}
			}
			placed.add(curr.ind);
		}
		int intervals = 0;
		int area = 0;
		for (int i = 0; i < N; i++) {
			if (intervals > 0 && i != 0) {
				area += haybales[i].pos - haybales[i - 1].pos;
			}
			if (trapped[i][0] == 2) {
				intervals--;
			}
			if (trapped[i][1] == 1) {
				intervals++;
			}
		}
		out.println(area);
		out.close();
		in.close();
	}

	static class Haybale {
		int size;
		int pos;
		int ind;

		public Haybale(int size, int pos) {
			this.size = size;
			this.pos = pos;
		}
	}

	static class Pos implements Comparator<Haybale> {
		public int compare(Haybale a, Haybale b) {
			return a.pos - b.pos;
		}
	}

	static class Size implements Comparator<Haybale> {
		public int compare(Haybale a, Haybale b) {
			return b.size - a.size;
		}
	}
}