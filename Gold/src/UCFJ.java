import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class UCFJ {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(tk.nextToken());
        int[] breeds = new int[N];
        tk = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            breeds[i] = Integer.parseInt(tk.nextToken());
        }
        int delegations = 0;
        for (int i = 0; i < N; i++) {
            int leader1 = breeds[i];
            boolean[] hasBreed = new boolean[N + 1];
            hasBreed[leader1] = true;
            for (int j = i + 1; j < N; j++) {
                int leader2 = breeds[j];
                if (!hasBreed[leader2]) {
                    delegations++;
                }
                if (leader2 == leader1) {
                    break;
                }
                hasBreed[leader2] = true;
            }
        }
        System.out.println(delegations);
        in.close();
    }
}
