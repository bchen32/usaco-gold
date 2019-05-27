import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Cowpatibility {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Cowpatibility\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Cowpatibility\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("cowpatibility.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		for (int i = 0; i < N; i++) {
			
		}
		out.close();
		in.close();
	}
}