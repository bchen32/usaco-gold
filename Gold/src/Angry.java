import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Angry {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Angry\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("angry.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		int N = Integer.parseInt(in.readLine());
		int[] cows = new int[N];
		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(cows);
		int maxRad = ((cows[N - 1] - cows[0] + 1) / 2) + 1;
		bSearch(1, maxRad);
		out.close();
		in.close();
	}
	
	public static int bSearch(int l, int h) {
		int r = (l + h) / 2;
		while (l < h) {
			
		}
		return r;
	}
}