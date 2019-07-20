import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;

public class Art2 {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Art\\1.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Art\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("art2.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("art2.out")));
		int N = Integer.parseInt(in.readLine());
		int[] painting = new int[N + 2];
		int[] colorStart = new int[N + 1];
		int[] colorEnd = new int[N + 1];
		painting[0] = painting[N + 1] = 0;
		Arrays.fill(colorStart, Integer.MAX_VALUE);
		Arrays.fill(colorEnd, -1);
		colorStart[0] = 0;
		colorEnd[0] = N + 1;
		for (int i = 0; i < N; i++) {
			painting[i + 1] = Integer.parseInt(in.readLine());
			int color = painting[i + 1];
			colorStart[color] = Math.min(colorStart[color], i + 1);
			colorEnd[color] = Math.max(colorEnd[color], i + 1);
		}
		
		Stack<Integer> stack = new Stack<Integer>();
		int layers = 0;
		for (int i = 0; i < N + 2; i++) {
			int currColor = painting[i];
			if (colorStart[currColor] == i) {
				stack.push(currColor);
				layers = Math.max(layers, stack.size());
			}
			if (stack.peek() != currColor) {
				layers = 0;
				break;
			}
			if (colorEnd[currColor] == i) {
				stack.pop();
			}
		}
		out.println(layers - 1);
		out.close();
		in.close();
	}
}