import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class Art2 {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Art2\\2.in"));
		BufferedReader in = new BufferedReader(new FileReader("art2.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("art2.out")));
		int N = Integer.parseInt(in.readLine());
		int[] painting = new int[N + 2];
		int[][] startEnd = new int[N + 1][2];
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < 2; j++) {
				startEnd[i][j] = -1;
			}
		}
		for (int i = 0; i < N; i++) {
			painting[i + 1] = Integer.parseInt(in.readLine());
		}
		for (int i = 0; i < N + 2; i++) {
			if (startEnd[painting[i]][0] == -1) {
				startEnd[painting[i]][0] = i;
				startEnd[painting[i]][1] = i;
			} else {
				startEnd[painting[i]][1] = i;
			}
		}
		Stack<Integer> stack = new Stack<Integer>();
		int maxLayers = 0;
		for (int i = 0; i < N + 2; i++) {
			int color = painting[i];
			if (color == 0 && i > 0 && painting[i - 1] != 0) {
				if (startEnd[painting[i - 1]][1] != i - 1) {
					maxLayers = 0;
					break;
				}
			}
			int start = startEnd[color][0];
			int end = startEnd[color][1];
			if (i == start) {
				stack.push(color);
			}
			maxLayers = Math.max(maxLayers, stack.size());
			if (i == end) {
				if (stack.peek() == color) {
					stack.pop();
				} else {
					maxLayers = 0;
					break;
				}
			}
		}
		out.println(maxLayers - 1);
		out.close();
		in.close();
	}
}