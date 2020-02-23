import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Boards {

	static QuadNode root;
	static int A;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\boards\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("boards.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("boards.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int P = Integer.parseInt(tk.nextToken());
		Spring[] springs = new Spring[P];
		for (int i = 0; i < P; i++) {
			tk = new StringTokenizer(in.readLine());
			int[] temp = new int[4];
			for (int j = 0; j < 4; j++) {
				temp[j] = Integer.parseInt(tk.nextToken());
			}
			springs[i] = new Spring(temp);
		}
		Arrays.sort(springs);
		int[] rootArr = { 0, 0, N, N };
		root = new QuadNode(new Spring(rootArr));
		for (int i = 0; i < P; i++) {
			int queryVal = query(root, new Spring(new Point(0, 0), springs[i].p1));
			insert(root, springs[i].p2,
					springs[i].p2.x - springs[i].p1.x + springs[i].p2.y - springs[i].p1.y + queryVal);
		}
		out.println(2 * N - query(root, new Spring(rootArr)));
		out.close();
		in.close();
	}

	static void insert(QuadNode n, Point p, int val) {
		if (n.spring.p1.x == n.spring.p2.x && n.spring.p1.y == n.spring.p2.y) {
			n.max = val;
			return;
		}
		int newMax = 0;
		for (int i = 0; i < 4; i++) {
			if (INR(QUAD(n.spring, i), p)) {
				insert(CH(n, i), p, val);
			}
			newMax = Math.max(newMax, n.children[i] == null ? 0 : n.children[i].max);
		}
		n.max = newMax;
		return;
	}

	static int query(QuadNode n, Spring s) {
		if (n == root) {
			A = 0;
		}
		if (n.max < A) {
			A = Math.max(A, n.max);
			return n.max;
		}
		if (INS(s, n.spring)) {
			A = Math.max(A, n.max);
			return n.max;
		}
		if (!(INR(s, n.spring.p1) || INR(s, n.spring.p2) || INR(n.spring, s.p1) || INR(n.spring, s.p2))) {
			return 0;
		}
		int q = 0;
		for (int i = 0; i < 4; i++) {
			q = Math.max(q, n.children[i] == null ? 0 : query(n.children[i], s));
		}
		A = Math.max(A, q);
		return q;
	}

	static Spring QUAD(Spring s, int d) {
		Point a = new Point((d & 1) == 1 ? (s.p1.x + s.p2.x) / 2 + 1 : s.p1.x,
				(d & 2) == 2 ? (s.p1.y + s.p2.y) / 2 + 1 : s.p1.y);
		Point b = new Point((d & 1) == 1 ? s.p2.x : (s.p1.x + s.p2.x) / 2,
				(d & 2) == 2 ? s.p2.y : (s.p1.y + s.p2.y) / 2);
		return new Spring(a, b);
	}

	static boolean INR(Spring s, Point p) {
		return s.p1.x <= p.x && s.p2.x >= p.x && s.p1.y <= p.y && s.p2.y >= p.y;
	}

	static QuadNode CH(QuadNode n, int d) {
		if (n.children[d] == null) {
			return n.children[d] = new QuadNode(QUAD(n.spring, d));
		}
		return n.children[d];
	}

	static boolean INS(Spring r, Spring s) {
		return INR(r, s.p1) && INR(r, s.p2);
	}

	static class Spring implements Comparable<Spring> {
		Point p1;
		Point p2;

		public Spring(int[] points) {
			p1 = new Point(points[0], points[1]);
			p2 = new Point(points[2], points[3]);
		}

		public Spring(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		@Override
		public int compareTo(Spring o) {
			if (p1.x == o.p1.x) {
				return p1.y - o.p1.y;
			}
			return p1.x - o.p1.x;
		}
	}

	static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class QuadNode {
		Spring spring;
		QuadNode[] children = new QuadNode[4];
		int max = 0;

		public QuadNode(Spring spring) {
			this.spring = spring;
		}
	}
}