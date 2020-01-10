import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Balance {

	public static void main(String[] args) throws IOException {
		// BufferedReader in = new BufferedReader(new
		// FileReader("D:\\Java\\USACO-Gold\\Gold\\Balance\\8.in"));
		BufferedReader in = new BufferedReader(new FileReader("balance.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("balance.out")));
		StringTokenizer tk = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tk.nextToken());
		int[] orig = new int[2 * N];
		tk = new StringTokenizer(in.readLine());
		for (int i = 0; i < 2 * N; i++) {
			orig[i] = Integer.parseInt(tk.nextToken());
		}
		// numInversions
		long left = 0;
		long right = 0;
		// num 1s
		long left1 = 0;
		long right1 = 0;
		// for left to right swap
		int rightMost1 = -1;
		int leftMost0 = -1;
		long rightMost0s = 0;
		long leftMost1s = 0;
		// for right to left swap
		int rightMost0 = -1;
		int leftMost1 = -1;
		long rightMost1s = 0;
		long leftMost0s = 0;
		for (int i = 0; i < N; i++) {
			if (orig[i] == 1) {
				left1++;
				rightMost1 = i;
				rightMost0s = 0;
				rightMost1s++;
			}
			if (orig[i] == 0) {
				left += left1;
				rightMost0s++;
				rightMost0 = i;
				rightMost1s = 0;
			}
		}
		for (int i = N; i < 2 * N; i++) {
			if (orig[i] == 1) {
				right1++;
				if (leftMost0 == -1) {
					leftMost1s++;
				}
				if (leftMost1 == -1) {
					leftMost1 = i;
				}
			}
			if (orig[i] == 0) {
				right += right1;
				if (leftMost0 == -1) {
					leftMost0 = i;
				}
				if (leftMost1 == -1) {
					leftMost0s++;
				}
			}
		}
		int[] arr = new int[2 * N];
		System.arraycopy(orig, 0, arr, 0, 2 * N);
		long ans = Math.abs(left - right);
		// swap left to right
		long onesL = left1;
		long onesR = right1;
		long leftInversions = left;
		long rightInversions = right;
		long moves = 0;
		while (onesL > 0) {
			// leftMost0 and rightMost1 guaranteed to not be -1
			// setup swap on the left
			moves += N - rightMost1 - 1;
			leftInversions -= rightMost0s;
			arr[rightMost1] = 0;
			arr[N - 1] = 1;
			rightMost0s--;
			// setup swap on the right
			moves += leftMost0 - N;
			rightInversions -= leftMost1s;
			arr[leftMost0] = 1;
			arr[N] = 0;
			leftMost1s--;
			// central swap
			leftInversions += onesL - 1;
			onesL--;
			rightInversions += N - onesR - 1;
			onesR++;
			moves++;
			arr[N] = 1;
			arr[N - 1] = 0;
			rightMost0s++;
			leftMost1s++;
			// recalculate
			for (int i = rightMost1; i >= 0; i--) {
				if (arr[i] == 1) {
					rightMost1 = i;
					break;
				} else {
					rightMost0s++;
				}
			}
			for (int i = leftMost0; i < 2 * N; i++) {
				if (arr[i] == 0) {
					leftMost0 = i;
					break;
				} else {
					leftMost1s++;
				}
			}
			// move the remaining
			moves += Math.abs(leftInversions - rightInversions);
			ans = Math.min(ans, moves);
			moves -= Math.abs(leftInversions - rightInversions);
		}
		System.arraycopy(orig, 0, arr, 0, 2 * N);
		// swap right to left
		onesL = left1;
		onesR = right1;
		leftInversions = left;
		rightInversions = right;
		moves = 0;
		while (onesR > 0) {
			// leftMost1 and rightMost0 guaranteed to not be -1
			// setup swap on the left
			moves += N - rightMost0 - 1;
			leftInversions += rightMost1s;
			arr[rightMost0] = 1;
			arr[N - 1] = 0;
			rightMost1s--;
			// setup swap on the right
			moves += leftMost1 - N;
			rightInversions += leftMost0s;
			arr[leftMost1] = 0;
			arr[N] = 1;
			leftMost0s--;
			// central swap
			leftInversions -= onesL;
			onesL++;
			rightInversions -= N - onesR;
			onesR--;
			moves++;
			arr[N] = 0;
			arr[N - 1] = 1;
			rightMost1s++;
			leftMost0s++;
			// recalculate
			for (int i = rightMost0; i >= 0; i--) {
				if (arr[i] == 0) {
					rightMost0 = i;
					break;
				} else {
					rightMost1s++;
				}
			}
			for (int i = leftMost1; i < 2 * N; i++) {
				if (arr[i] == 1) {
					leftMost1 = i;
					break;
				} else {
					leftMost0s++;
				}
			}
			// move the remaining
			moves += Math.abs(leftInversions - rightInversions);
			ans = Math.min(ans, moves);
			moves -= Math.abs(leftInversions - rightInversions);
		}
		out.println(ans);
		out.close();
		in.close();
	}
}