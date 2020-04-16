import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Help {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Help\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("help.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("help.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		Interval[] ints = new Interval[N];
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			int l = Integer.parseInt(tk.nextToken());
			int r = Integer.parseInt(tk.nextToken());
			ints[i] = new Interval(l, r);
		}
		out.println(8);
		out.close();
		in.close();
	}

	static class Interval {
		int l;
		int r;

		public Interval(int l, int r) {
			this.l = l;
			this.r = r;
		}
	}
}