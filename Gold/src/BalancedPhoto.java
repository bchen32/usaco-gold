import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BalancedPhoto {

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\BalancedPhoto\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("bphoto.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
		StringTokenizer ln = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(ln.nextToken());
		for (int i = 0; i < N; i++) {
			
		}
		out.close();
		in.close();
	}
}