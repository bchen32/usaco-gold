public class Delegation {

	public static void main(String[] args) {
		int N = 5;
		int[] a = { 9, 3, 5, 3, 1 };
		int[] b = { 6, 4, 7, 7, 7 };
		int[][][] dp = new int[N + 1][4][2];
		for (int i = 1; i <= N; i++) {
			for (int consec = 1; consec < 4 && consec <= i; consec++) {
				if (consec == 1) {
					// if only 1 in a row is allowed
					int maxPrevA = 0;
					int maxPrevB = 0;
					for (int j = 1; j < 4; j++) {
						maxPrevA = Math.max(maxPrevA, dp[i - 1][j][0]);
						maxPrevB = Math.max(maxPrevB, dp[i - 1][j][1]);
					}
					dp[i][1][0] = maxPrevB + a[i - 1];
					dp[i][1][1] = maxPrevA + b[i - 1];
				} else {
					// if more than 1 in a row is allowed
					dp[i][consec][0] = dp[i - 1][consec - 1][0] + a[i - 1];
					dp[i][consec][1] = dp[i - 1][consec - 1][1] + b[i - 1];
				}
			}
		}
		// looking in the last row for answer
		// basically looking at all dp states which have time=N seconds
		int ans = 0;
		for (int j = 1; j < 4; j++) {
			for (int k = 0; k < 2; k++) {
				ans = Math.max(ans, dp[N][j][k]);
			}
		}
		System.out.println(ans);
	}
}