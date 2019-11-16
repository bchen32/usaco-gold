import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Sabotage {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Sabotage\\8.in"));
		BufferedReader in = new BufferedReader(new FileReader("sabotage.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sabotage.out")));
		int N = Integer.parseInt(in.readLine());
		int[] arr = new int[N];
		double total = 0;
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(in.readLine());
			total += arr[i];
		}
		double ans = lowerBound(arr, total);
		String output = String.format("%.3f", ans);
		out.println(output);
		out.close();
		in.close();
	}

	static boolean check(int[] arr, double checkAvg, double total) {
		int N = arr.length;
		total -= checkAvg * N;
		double[] subtract = new double[N];
		for (int i = 0; i < N; i++) {
			subtract[i] = arr[i] - checkAvg;
		}
		double localMax = subtract[1];
		double globalMax = Integer.MIN_VALUE;
		for (int i = 1; i < N - 1; i++) {
			localMax = Math.max(subtract[i], localMax + subtract[i]);
			if (localMax > globalMax) {
				globalMax = localMax;
			}
		}
		double leftOver = total - globalMax;
		return leftOver <= 0;
	}

	static double lowerBound(int[] arr, double total) {
		double low = 0;
		double high = total;
		while (low + .00001 < high) {
			double mid = (low + high) / 2;
			if (check(arr, mid, total)) {
				high = mid;
			} else {
				low = mid + .00001;
			}
		}
		return low;
	}
}