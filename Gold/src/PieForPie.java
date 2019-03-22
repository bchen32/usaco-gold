import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class PieForPie {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\PieForPie\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\PieForPie\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("piepie.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int D = Integer.parseInt(tk.nextToken());
		Pie[] bessie = new Pie[N];
		Pie[] elsie = new Pie[N];
		int[] dists = new int[N];
		for (int i = 0; i < 2 * N; i++) {
			
		}
		out.close();
		in.close();
	}
}

class Pie {
	int bScore;
	int eScore;
	int num;
	
	public Pie(int bScore, int eScore) {
		this.bScore = bScore;
		this.eScore = eScore;
	}
}