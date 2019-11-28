import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Moovie {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Moovie\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("movie.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("movie.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int L = Integer.parseInt(tk.nextToken());
		Movie[] movies = new Movie[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int d = Integer.parseInt(tk.nextToken());
			int c = Integer.parseInt(tk.nextToken());
			TreeSet<Integer> showtimes = new TreeSet<Integer>();
			for (int j = 0; j < c; j++) {
				showtimes.add(Integer.parseInt(tk.nextToken()));
			}
			movies[i] = new Movie(d, showtimes);
		}
		int[] dp = new int[1 << N];
		boolean[] singles = new boolean[1 << N];
		for (int i = 0; i < N; i++) {
			boolean[] bool = new boolean[N];
			bool[i] = true;
			int bin = toBin(bool);
			if (movies[i].showtimes.first() == 0) {
				dp[bin] = movies[i].duration;
			}
			singles[bin] = true;
		}
		int least = Integer.MAX_VALUE;
		for (int i = 1; i < 1 << N; i++) {
			if (singles[i]) {
				continue;
			}
			int val = i;
			for (int j = 0; j < N; j++) {
				if ((i & (1 << j)) > 0) {
					val &= ~(1 << j);
					if (movies[j].showtimes.floor(dp[val]) == null) {
						dp[i] = Math.max(dp[i], 0);
						continue;
					}
					int start = movies[j].showtimes.floor(dp[val]);
					if (start > dp[val]) {
						dp[i] = Math.max(dp[i], dp[val]);
					} else {
						int end = start + movies[j].duration;
						dp[i] = Math.max(dp[i], Math.max(dp[val], end));
					}
					val |= (1 << j);
				}
			}
			if (dp[i] >= L) {
				least = Math.min(least, numSet(i));
			}
		}
		out.println(least == Integer.MAX_VALUE ? -1 : least);
		out.close();
		in.close();
	}

	static int toBin(boolean[] arr) {
		int binary = 0;
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i]) {
				binary |= (1 << 0);
			}
			if (i != 0) {
				binary <<= 1;
			}
		}
		return binary;
	}

	static int numSet(int bin) {
		int count = 0;
		while (bin > 0) {
			count += bin & 1;
			bin >>= 1;
		}
		return count;
	}

	static class Movie {
		int duration;
		TreeSet<Integer> showtimes;

		public Movie(int duration, TreeSet<Integer> showtimes) {
			this.duration = duration;
			this.showtimes = showtimes;
		}
	}
}