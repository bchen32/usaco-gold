import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Censoring {
	
	static int S;

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Censoring\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("censor.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		S = Integer.parseInt(ln.nextToken());
		for (int i = 0; i < S; i++) {
			
		}
		out.close();
		in.close();
	}
}