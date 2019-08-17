import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Balance {
	
	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bench\\git\\USACO-Gold\\Gold\\Balance\\5.in"));
		// BufferedReader in = new BufferedReader(new FileReader("H:\\git\\USACO-Gold\\Gold\\Balance\\1.in"));
		BufferedReader in = new BufferedReader(new FileReader("balance.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("balance.out")));
		int N = Integer.parseInt(in.readLine());
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int[] orig = new int[2 * N];
		int ones1 = 0;
		int ones2 = 0;
		int l0 = -1;
		int l1 = -1;
		int r0 = -1;
		int r1 = -1;
		
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		for (int i = 0; i < N; i++) {
			orig[i] = Integer.parseInt(tk.nextToken());
			if (orig[i] == 1) {
				ones1++;
				l1 = i;
				x1++;
				x2 = 0;
			} else {
				l0 = i;
				x1 = 0;
				x2++;
			}
		}
		for (int i = 0; i < N; i++) {
			orig[N + i] = Integer.parseInt(tk.nextToken());
			if (orig[N + i] == 1) {
				ones2++;
				if (r1 == -1) {
					r1 = N + i;
				}
			} else if (r0 == -1) {
				r0 = N + i;
			}
			if (r1 == -1) {
				y1++;
			}
			if (r0 == -1) {
				y2++;
			}
		}
		int[] arr = new int[2 * N];
		System.arraycopy(orig, 0, arr, 0, 2 * N);
		// Move 1s left
		long i1 = inv(arr, 0);
		long i2 = inv(arr, N);
		long best = Math.abs(i1 - i2);
		long moves = 0;
		int o1 = ones1;
		int o2 = ones2;
		while (o2 > 0) {
			moves++;
			if (arr[N - 1] != 0) {
				moves += N - 1 - l0;
				arr[l0] = 1;
				arr[N - 1] = 0;
				i1 += x1;
			}
			if (arr[N] != 1) {
				moves += r1 - N;
				arr[r1] = 0;
				arr[N] = 1;
				i2 += y1;
			}
			i1 -= o1;
			i2 -= (N - o2);
			arr[N - 1] = 1;
			arr[N] = 0;
			for (int i = l0; i >= 0; i--) {
				if (arr[i] == 0) {
					l0 = i;
					break;
				} else {
					x1++;
				}
			}
			for (int i = r1; i < 2 * N; i++) {
				if (arr[i] == 1) {
					r1 = i;
					break;
				} else {
					y1++;
				}
			}
			moves += Math.abs(i1 - i2);
			best = Math.min(best, moves);
			moves -= Math.abs(i1 - i2);
			o1++;
			o2--;
		}
		// Move 1s right
		System.arraycopy(orig, 0, arr, 0, 2 * N);
		i1 = inv(arr, 0);
		i2 = inv(arr, N);
		moves = 0;
		o1 = ones1;
		o2 = ones2;
		while (o1 > 0) {
			moves++;
			if (arr[N - 1] != 1) {
				moves += N - 1 - l1;
				arr[l1] = 0;
				arr[N - 1] = 1;
				i1 -= x2;
			}
			if (arr[N] != 0) {
				moves += r0 - N;
				arr[r0] = 1;
				arr[N] = 0;
				i2 -= y2;
			}
			i1 += o1 - 1;
			i2 += (N - o2) - 1;
			arr[N - 1] = 0;
			arr[N] = 1;
			for (int i = l1; i >= 0; i--) {
				if (arr[i] == 1) {
					l1 = i;
					break;
				} else {
					x2++;
				}
			}
			for (int i = r0; i < 2 * N; i++) {
				if (arr[i] == 0) {
					r0 = i;
					break;
				} else {
					y2++;
				}
			}
			moves += Math.abs(i1 - i2);
			best = Math.min(best, moves);
			moves -= Math.abs(i1 - i2);
			o1--;
			o2++;
		}
		out.println(best);
		out.close();
		in.close();
	}
	
	static long inv(int[] arr, int offset) {
		long inv = 0;
		long num1s = 0;
		for (int i = 0; i < arr.length / 2; i++) {
			if (arr[i + offset] == 1) {
				num1s++;
			} else {
				inv += num1s;
			}
		}
		return inv;
	}
}