import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Censoring {
	
	static String S;

	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Censoring\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("censor.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		S = in.readLine();
		int t = Integer.parseInt(in.readLine());
		String[] censors = new String[t];
		for (int i = 0; i < t; i++) {
			censors[i] = in.readLine();
		}
		
		out.close();
		in.close();
	}
}