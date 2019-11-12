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
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\EmptyStalls\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("empty.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("empty.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int K = Integer.parseInt(tk.nextToken());
		int[] stalls = new int[N];
		for (int i = 0; i < K; i++) {
			tk = new StringTokenizer(in.readLine());

		}
		out.close();
		in.close();
	}
}