import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class EmptyStalls {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\EmptyStalls\\5.in"));
		BufferedReader in = new BufferedReader(new FileReader("empty.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("empty.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[] stalls = new int[N];
		for (int i = 0; i < K; i++) {
			tk = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tk.nextToken());
			int y = Integer.parseInt(tk.nextToken());
			long a = Long.parseLong(tk.nextToken());
			long b = Long.parseLong(tk.nextToken());
			for (int j = 1; j <= y; j++) {
				int ind = (int) ((a * j + b) % N);
				stalls[ind] += x;
			}
		}
		long runningSum = 0;
		for (int i = 0; i < N; i++) {
			runningSum += stalls[i];
			if (runningSum > 0) {
				runningSum--;
			}
		}
		for (int i = 0; i < N; i++) {
			runningSum += stalls[i];
			if (runningSum > 0) {
				runningSum--;
			} else {
				out.println(i);
				break;
			}
		}
		out.close();
		in.close();
	}
}