import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class Angry {

	static int N;
	static TreeSet<Integer> positions;

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Angry\\4.in"));
		BufferedReader in = new BufferedReader(new FileReader("angry.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		N = Integer.parseInt(in.readLine());
		positions = new TreeSet<Integer>();
		TreeSet<Integer> inputs = new TreeSet<Integer>();
		for (int i = 0; i < N; i++) {
			inputs.add(2 * Integer.parseInt(in.readLine()));
		}
		int min = inputs.first();
		for (int i = 0; i < N; i++) {
			positions.add(inputs.pollFirst() - min);
		}
		double ans = bsearchRadius() / 2;
		String formatted = String.format("%.1f", ans);
		out.println(formatted);
		out.close();
		in.close();
	}

	static double bsearchRadius() {
		int low = 1;
		int high = positions.last() - positions.first();
		while (high - low > 1) {
			int mid = (low + high + 1) / 2;
			boolean success = bsearchStart(mid);
			if (!success) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return bsearchStart(low) ? low : high;
	}

	static boolean bsearchStart(int radius) {
		int low = positions.first();
		int high = positions.last();
		while (high - low > 1) {
			long mid = (low + high + 1) / 2;
			if (mid == 1100202307) {
				System.out.println();
			}
			boolean success = checkRight((int) mid, radius);
			if (!success) {
				low = (int) mid;
			} else {
				high = (int) mid;
			}
		}
		return checkLeft(checkRight(low, radius) ? low : high, radius);
	}

	static boolean checkLeft(int start, int rad) {
		while (start > positions.first()) {
			int next = positions.ceiling(start - rad);
			if (next == start) {
				return false;
			}
			start = next;
			rad -= 2;
		}
		return true;
	}

	static boolean checkRight(int start, int rad) {
		while (start < positions.last()) {
			int next = positions.floor(start + rad);
			if (next == start) {
				return false;
			}
			start = next;
			rad -= 2;
		}
		return true;
	}
}