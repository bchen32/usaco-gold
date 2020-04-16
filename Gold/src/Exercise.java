import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Exercise {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Exercise\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("exercise.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("exercise.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int M = Integer.parseInt(tk.nextToken());
		long ans = 0;
		for (int i = 1; i < 100000; i++) {
			int sum = sum(i, N);
			if (sum <= N || i <= N) {
				ans += i;
				ans %= M;
			}
		}
		out.println(ans);
		out.close();
		in.close();
	}

	static int sum(int x, int N) {
		int sum = 0;
		if (x % 2 == 0) {
			int minisum = 1;
			while (x % 2 == 0) {
				minisum *= 2;
				x /= 2;
			}
			sum += minisum;
		}
		for (int i = 3; i <= Math.sqrt(x); i += 2) {
			if (x % i == 0) {
				int minisum = 1;
				while (x % i == 0) {
					minisum *= i;
					x /= i;
				}
				sum += minisum;
			}
		}
		if (x > 2) {
			sum += x;
		}
		return sum;
	}
}