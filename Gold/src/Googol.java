import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Googol {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println("Answer " + count(1));
        in.close();
    }

    static int countGiven(int curr, int x) throws IOException {
        if (curr == 0) {
            return 0;
        }
        System.out.println(curr);
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int leftNum = Integer.parseInt(tk.nextToken());
        int rightNum = Integer.parseInt(tk.nextToken());
        if (x % 2 == 1) {
            int r = (x - 1) / 2;
            int l = countGiven(leftNum, r);
            return 1 + l + r;
        } else {
            int l = x / 2;
            int r = countGiven(rightNum, l - 1);
            return 1 + l + r;
        }

    }

    static int count(int curr) throws IOException {
        if (curr == 0) {
            return 0;
        }
        System.out.println(curr);
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int leftNum = Integer.parseInt(tk.nextToken());
        int rightNum = Integer.parseInt(tk.nextToken());
        int r = count(rightNum);
        int l = countGiven(leftNum, r);
        return 1 + l + r;
    }
}