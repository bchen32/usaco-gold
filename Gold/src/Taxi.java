import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Taxi {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\taxi\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("taxi.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("taxi.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		int[] start = new int[N + 1];
		int[] end = new int[N + 1];
		long withCow = 0;
		for (int i = 0; i < N; i++) {
			tk = new StringTokenizer(in.readLine());
			start[i] = Integer.parseInt(tk.nextToken());
			end[i] = Integer.parseInt(tk.nextToken());
			withCow += Math.abs(start[i] - end[i]);
		}
		start[N] = M;
		end[N] = 0;
		Arrays.sort(start);
		Arrays.sort(end);
		long withoutCow = 0;
		for (int i = 0; i < N + 1; i++) {
			withoutCow += Math.abs(start[i] - end[i]);
		}
		out.println(withCow + withoutCow);
		out.close();
		in.close();
	}
}