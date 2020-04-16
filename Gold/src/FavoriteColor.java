import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FavoriteColor {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("D:\\Java\\USACO-Gold\\Gold\\FavoriteColor\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("fcolor.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fcolor.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		for (int i = 0; i < N; i++) {
			
		}
		out.close();
		in.close();
	}
}