import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CowRectangles {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\CowRectangles\\5.in"));
		BufferedReader in = new BufferedReader(new FileReader("cowrect.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowrect.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		Cow[] cows = new Cow[N];
		TreeSet<Integer> cols = new TreeSet<Integer>();
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int r = Integer.parseInt(tk.nextToken());
			int c = Integer.parseInt(tk.nextToken());
			String breed = tk.nextToken();
			cows[i] = new Cow(r, c, breed.equals("H"));
			cols.add(c);
		}
		Arrays.sort(cows);
		ArrayList<Integer> cList = new ArrayList<Integer>();
		for (int c : cols) {
			cList.add(c);
		}
		int minArea = 0;
		int maxNum = 0;
		for (int i = 0; i < cList.size(); i++) {
			for (int j = i + 1; j < cList.size(); j++) {
				boolean valid = false;
				int lastX = -1;
				int now = 0;
				for (int a = 0; a < N;) {
					int b = a;
					int numHol = 0;
					int numGurn = 0;
					while (b < N && cows[a].r == cows[b].r) {
						if (cows[b].c >= cList.get(i) && cows[b].c <= cList.get(j)) {
							if (cows[b].hol) {
								numHol++;
							} else {
								numGurn++;
							}
						}
						b++;
					}
					if (numGurn > 0) {
						valid = false;
						now = 0;
					} else if (numHol + numGurn > 0) {
						if (!valid) {
							valid = true;
							lastX = cows[a].r;
						}
						now += numHol;
						int currArea = (cList.get(j) - cList.get(i)) * (cows[a].r - lastX);
						if (now > maxNum || (now == maxNum && currArea < minArea)) {
							maxNum = now;
							minArea = currArea;
						}
					}
					a = b;
				}
			}
		}
		out.println(maxNum + "\n" + minArea);
		out.close();
		in.close();
	}

	static class Cow implements Comparable<Cow> {
		int r;
		int c;
		boolean hol;

		public Cow(int r, int c, boolean hol) {
			this.r = r;
			this.c = c;
			this.hol = hol;
		}

		@Override
		public int compareTo(Cow o) {
			return this.r - o.r;
		}
	}
}