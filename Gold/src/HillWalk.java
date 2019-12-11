import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class HillWalk {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\HillWalk\\8.in"));
		BufferedReader in = new BufferedReader(new FileReader("hillwalk.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hillwalk.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		Hill[] hills = new Hill[N];
		Point[] points = new Point[2 * N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int x1 = Integer.parseInt(tk.nextToken());
			int y1 = Integer.parseInt(tk.nextToken());
			int x2 = Integer.parseInt(tk.nextToken());
			int y2 = Integer.parseInt(tk.nextToken());
			hills[i] = new Hill(x1, y1, x2, y2, i);
			points[i * 2] = new Point(x1, y1, i, true);
			points[i * 2 + 1] = new Point(x2, y2, i, false);
		}
		Arrays.sort(points);
		TreeSet<Hill> currHills = new TreeSet<Hill>();
		Hill curr = hills[points[0].hill];
		int maxNum = 1;
		currHills.add(hills[points[0].hill]);
		for (int i = 1; i < 2 * N; i++) {
			Point p = points[i];
			if (!p.start) {
				if (curr == null || p == null) {
					continue;
				}
				if (p.hill == curr.ind) {
					if (curr == currHills.first()) {
						break;
					}
					curr = currHills.lower(curr);
					maxNum++;
				}
				currHills.remove(hills[p.hill]);
			} else {
				currHills.add(hills[p.hill]);
			}
		}
		out.println(maxNum);
		out.close();
		in.close();
	}

	static class Hill implements Comparable<Hill> {
		int x1;
		int y1;
		int x2;
		int y2;
		int ind;

		public Hill(int x1, int y1, int x2, int y2, int ind) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.ind = ind;
		}

		@Override
		public int compareTo(Hill o) {
			if (ind == o.ind) {
				return 0;
			}
			if (x2 < o.x2) {
				long a = (long) (y2 - o.y1) * (long) (o.x2 - o.x1);
				long b = (long) (o.y2 - o.y1) * (long) (x2 - o.x1);
				return Long.compare(a, b);
			}
			long a = (long) (y2 - y1) * (long) (o.x2 - x1);
			long b = (long) (o.y2 - y1) * (long) (x2 - x1);
			return Long.compare(a, b);
		}
	}

	static class Point implements Comparable<Point> {
		int x;
		int y;
		int hill;
		boolean start;

		public Point(int x, int y, int hill, boolean start) {
			this.x = x;
			this.y = y;
			this.hill = hill;
			this.start = start;
		}

		@Override
		public int compareTo(Point o) {
			if (x == o.x) {
				return y - o.y;
			}
			return x - o.x;
		}
	}
}