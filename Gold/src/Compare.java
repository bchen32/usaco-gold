import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Compare {

    public static void main(String[] args) throws IOException {
        BufferedReader f1 = new BufferedReader(new FileReader("balance.out"));
        BufferedReader f2 = new BufferedReader(new FileReader("balancetest.out"));
        for (int i = 0; i < 55; i++) {
            StringTokenizer tk1 = new StringTokenizer(f1.readLine());
            StringTokenizer tk2 = new StringTokenizer(f2.readLine());
            for (int j = 0; j < 200000; j++) {
                if (Integer.parseInt(tk1.nextToken()) != Integer.parseInt(tk2.nextToken())) {
                    System.out.println(i + " " + j);
                }
            }
        }
        f1.close();
        f2.close();
    }
}