import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SkiCourse {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\SkiCourse\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("skicourse.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skicourse.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int R = Integer.parseInt(tk.nextToken());
		int C = Integer.parseInt(tk.nextToken());
		int[][] field = new int[R][C];
		for (int i = 0; i < R; i++) {
			String ln = in.readLine();
			for (int j = 0; j < C; j++) {
				field[i][j] = ln.substring(j, j + 1).equals("S") ? 1 : 0;
			}
		}
		int[][] dpRough = new int[R + 1][C + 1];
		int[][] dpSmooth = new int[R + 1][C + 1];
		int[][] dpStamped = new int[R + 1][C + 1];
		int ans = Integer.MAX_VALUE;
		while (true) {
			dpRough = new int[R + 1][C + 1];
			dpSmooth = new int[R + 1][C + 1];
			dpStamped = new int[R + 1][C + 1];
			int best = -1;
			int endR = 0;
			int endC = 0;
			if (field[0][0] == 2) {
				dpStamped[1][1] = 1;
			}
			for (int i = 1; i < R + 1; i++) {
				for (int j = 1; j < C + 1; j++) {
					if (field[i - 1][j - 1] != 2) {
						continue;
					}
					dpStamped[i][j] = Math.min(dpStamped[i - 1][j],
							Math.min(dpStamped[i][j - 1], dpStamped[i - 1][j - 1])) + 1;
				}
			}
			if (field[0][0] == 0) {
				dpRough[1][1] = 1;
			}
			for (int i = 1; i < R + 1; i++) {
				for (int j = 1; j < C + 1; j++) {
					if (field[i - 1][j - 1] == 1) {
						continue;
					}
					dpRough[i][j] = Math.min(dpRough[i - 1][j], Math.min(dpRough[i][j - 1], dpRough[i - 1][j - 1])) + 1;
					if (best < dpRough[i][j] && dpRough[i][j] != dpStamped[i][j]) {
						best = dpRough[i][j];
						endR = i;
						endC = j;
					}
				}
			}
			if (field[0][0] == 1) {
				dpSmooth[1][1] = 1;
			}
			for (int i = 1; i < R + 1; i++) {
				for (int j = 1; j < C + 1; j++) {
					if (field[i - 1][j - 1] == 0) {
						continue;
					}
					dpSmooth[i][j] = Math.min(dpSmooth[i - 1][j], Math.min(dpSmooth[i][j - 1], dpSmooth[i - 1][j - 1]))
							+ 1;
					if (best < dpSmooth[i][j] && dpSmooth[i][j] != dpStamped[i][j]) {
						best = dpSmooth[i][j];
						endR = i;
						endC = j;
					}
				}
			}
			if (best == -1) {
				break;
			}
			ans = Math.min(ans, best);
			for (int i = endR - best; i < endR; i++) {
				for (int j = endC - best; j < endC; j++) {
					field[i][j] = 2;
				}
			}
		}
		out.println(ans);
		out.close();
		in.close();
	}
}