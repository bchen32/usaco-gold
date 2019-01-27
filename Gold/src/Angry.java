import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class Angry {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Angry\\3.in"));
		BufferedReader in = new BufferedReader(new FileReader("angry.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		int N = Integer.parseInt(in.readLine());
		TreeSet<Long> cows = new TreeSet<Long>();
		for (int i = 0; i < N; i++) {
			cows.add(2L * Integer.parseInt(in.readLine()));
		}
		long maxRad = (cows.last() - cows.first()) / 2;
		out.printf("%.1f\n", bSearch(0, maxRad, cows) / 2.0);
		out.close();
		in.close();
	}

	public static long bSearch(long l, long h, TreeSet<Long> list) {
		// Binary search possible radii

		// If a given radius works, then anything bigger is guaranteed to work and vice
		// versa
		long currR = (l + h) / 2;
		while (l < h) {
			currR = (l + h) / 2;
			long left = list.first();
			long right = list.last();
			// For a given radius, binary search the best point to start
			// If a given point can reach the left, then anything smaller works
			// If a given point cannot reach the left, then nothing bigger works
			while (left < right) {
				long currStart = (left + right + 1) / 2;
				if (checkLeft(list, currStart, currR)) {
					left = currStart;
				} else {
					right = currStart - 1;
				}
			}
			// Optimal starting point has been found (furthest right that can still reach
			// left)
			if (checkRight(list, left, currR)) {
				h = currR;
			} else {
				l = currR + 1;
			}
		}
		return l;
	}

	static boolean checkLeft(TreeSet<Long> list, long start, long r) {
		// For a given starting pos and radius, check if the explosion can reach all the
		// way left
		if (start - r <= list.first()) {
			// Can directly reach
			return true;
		}
		// Find lowest haybale in range
		long next = list.ceiling(start - r);
		// Everything is multiplied by two, so radius decrements by 2
		r -= 2;
		while (next != list.first()) {
			if (list.ceiling(next - r) >= next) {
				// Can't reach anything new
				return false;
			}
			next = list.ceiling(next - r);
			r -= 2;
		}
		return true;
	}

	static boolean checkRight(TreeSet<Long> list, long start, long r) {
		// For a given starting pos and radius, check it can reach right
		if (start + r >= list.last()) {
			// Can directly reach
			return true;
		}
		// Find largest haybale in range
		long next = list.floor(start + r);
		// Everything is multiplied by two, so radius decrements by 2
		r -= 2;
		while (next != list.last()) {
			if (list.floor(next + r) <= next) {
				// Can't reach new
				return false;
			}
			next = list.floor(next + r);
			r -= 2;
		}
		return true;
	}
}
