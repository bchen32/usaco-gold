import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class HPS {
	
	static int N;

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\HPS\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("hps.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		N = Integer.parseInt(ln.nextToken());
		for (int i = 0; i < N; i++) {
			
		}
		out.close();
		in.close();
	}
}