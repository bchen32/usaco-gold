import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class LightsOut {
	
	static Point[] points;
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\LightsOut\\8.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\LightsOut\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("lightsout.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lightsout.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		points = new Point[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			points[i] = new Point(x, y, i);
		}
		for (int i = 0; i < N; i++) {
			int x = points[i].x;
			int y = points[i].y;
			Point prev = points[(i - 1 + N) % N];
			Point next = points[(i + 1) % N];
			if (prev.x == x && next.y == y) {
				if (prev.y < y) {
					if (next.x > x) {
						points[i].angle = 0;
					} else {
						points[i].angle = 1;
					}
				} else {
					if (next.x > x) {
						points[i].angle = 1;
					} else {
						points[i].angle = 0;
					}
				}
			} else {
				if (prev.x < x) {
					if (next.y > y) {
						points[i].angle = 1;
					} else {
						points[i].angle = 0;
					}
				} else {
					if (next.y > y) {
						points[i].angle = 0;
					} else {
						points[i].angle = 1;
					}
				}
			}
		}
		int[] startDists = new int[N];
		for (int i = 1; i < N; i++) {
			int clock = 0;
			int counter = 0;
			for (int j = i + 1; j <= N; j++) {
				clock += dist(points[j % N], points[j - 1]);
			}
			for (int j = i - 1; j >= 0; j--) {
				counter += dist(points[j], points[j + 1]);
			}
			startDists[i] = Math.min(clock, counter);
		}
		int worst = 0;
		for (int i = 1; i < N; i++) {
			LinkedList<Point> possiblePoints = new LinkedList<Point>();
			Point curr = points[i];
			for (Point p : points) {
				if (p.ind == 0 || p.ind == i) {
					continue;
				}
				if (p.angle == curr.angle) {
					possiblePoints.add(p);
				}
			}
			int dist = 0;
			for (int j = i; j <= N; j++) {
				curr = points[j % N];
				int edge = dist(curr, points[(j + 1) % N]);
				if (possiblePoints.isEmpty() || curr.ind == 0) {
					break;
				}
				LinkedList<Point> pointOnly = new LinkedList<Point>();
				for (Point p : possiblePoints) {
					if (p.angle == curr.angle && p.ind != 0) {
						pointOnly.add(p);
					}
				}
				if (pointOnly.isEmpty()) {
					break;
				}
				LinkedList<Point> temp = new LinkedList<Point>();
				for (Point p : pointOnly) {
					if (dist(p, points[(p.ind + 1 + N) % N]) == edge) {
						temp.add(points[(p.ind + 1) % N]);
					}
				}
				dist += edge;
				possiblePoints = temp;
			}
			if (curr.ind != 0) {
				dist += startDists[curr.ind];
			}
			worst = Math.max(worst, dist - startDists[i]);
		}
		out.println(worst);
		out.close();
		in.close();
	}
	
	static int dist(Point a, Point b) {
		return Math.abs(a.x - b.x + a.y - b.y);
	}
	
	static class Point {
		int x;
		int y;
		int ind;
		int angle;
		
		public Point(int x, int y, int ind) {
			this.x = x;
			this.y = y;
			this.ind = ind;
		}
		
		@Override
		public String toString() {
			return Integer.toString(angle);
		}
	}
}