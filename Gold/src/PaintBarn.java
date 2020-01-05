import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class PaintBarn {

    public static void main(String[] args) throws IOException {
        // BufferedReader in = new BufferedReader(new
        // FileReader("D:\\Java\\USACO-Gold\\Gold\\PaintBarn\\6.in"));
        BufferedReader in = new BufferedReader(new FileReader("paintbarn.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("paintbarn.out")));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(tk.nextToken());
        int K = Integer.parseInt(tk.nextToken());
        int[][] input = new int[N][4];
        int minR = Integer.MAX_VALUE;
        int minC = Integer.MAX_VALUE;
        int maxR = 0;
        int maxC = 0;
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(in.readLine());
            for (int j = 0; j < 4; j++) {
                input[i][j] = Integer.parseInt(tk.nextToken());
            }
            minR = Math.min(minR, Math.min(input[i][0], input[i][2]));
            minC = Math.min(minC, Math.min(input[i][1], input[i][3]));
            maxR = Math.max(maxR, Math.max(input[i][0], input[i][2]));
            maxC = Math.max(maxC, Math.max(input[i][1], input[i][3]));
        }
        int R = maxR - minR + 1;
        int C = maxC - minC + 1;
        int[][] rectangles = new int[R][C];
        for (int i = 0; i < N; i++) {
            int r1 = input[i][0] - minR;
            int c1 = input[i][1] - minC;
            int r2 = input[i][2] - minR;
            int c2 = input[i][3] - minC;
            for (int r = r1; r < r2; r++) {
                rectangles[r][c1]++;
                rectangles[r][c2]--;
            }
        }
        int startArea = 0;
        int[][] layers = new int[R--][C--];
        for (int r = 0; r < R; r++) {
            int numPaint = 0;
            for (int c = 0; c < C; c++) {
                numPaint += rectangles[r][c];
                if (numPaint == K - 1) {
                    layers[r][c] = 1;
                } else if (numPaint == K) {
                    startArea++;
                    layers[r][c] = -1;
                }
            }
        }
        int ans = maxSumRectangle(layers, R, C);
        out.println(ans + startArea);
        out.close();
        in.close();
    }

    static int maxSumRectangle(int[][] arr, int R, int C) {
        int[][] pre = new int[R + 1][C];
        for (int i = 1; i < R + 1; i++) {
            for (int j = 0; j < C; j++) {
                pre[i][j] = pre[i - 1][j] + arr[i - 1][j];
            }
        }
        // topRects[i] is the maximum sum rectangle which is at or above r = i
        // similar story for the other 3
        int[] topRects = new int[R];
        int[] bottomRects = new int[R];
        int[] leftRects = new int[C];
        int[] rightRects = new int[C];
        for (int r1 = 0; r1 < R; r1++) {
            for (int r2 = r1; r2 < R; r2++) {
                int sum = 0;
                int startCol = 0;
                for (int col = 0; col < C; col++) {
                    int colSum = pre[r2 + 1][col] - pre[r1][col];
                    sum += colSum;
                    if (colSum >= sum) {
                        startCol = col;
                        sum = colSum;
                    }
                    topRects[r2] = Math.max(topRects[r2], sum);
                    bottomRects[r1] = Math.max(bottomRects[r1], sum);
                    leftRects[col] = Math.max(leftRects[col], sum);
                    rightRects[startCol] = Math.max(rightRects[startCol], sum);
                }
            }
        }
        int max = 0;
        for (int r1 = 0; r1 < R; r1++) {
            for (int r2 = r1 + 1; r2 < R; r2++) {
                max = Math.max(max, topRects[r1]);
                max = Math.max(max, bottomRects[r1]);
                max = Math.max(max, topRects[r2]);
                max = Math.max(max, bottomRects[r2]);
                max = Math.max(max, topRects[r1] + bottomRects[r2]);
            }
        }
        for (int c1 = 0; c1 < C; c1++) {
            for (int c2 = c1 + 1; c2 < C; c2++) {
                max = Math.max(max, leftRects[c1]);
                max = Math.max(max, rightRects[c1]);
                max = Math.max(max, leftRects[c2]);
                max = Math.max(max, rightRects[c2]);
                max = Math.max(max, leftRects[c1] + rightRects[c2]);
            }
        }
        return max;
    }
}