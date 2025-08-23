import java.util.*;

public class ElemeSolution3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 0; t < T; t++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int result = 0;
            for (int k = 1; k <= n; k++) {
                int rounds = calculateRounds(k, m);
                result ^= rounds;
            }
            System.out.println(result);
        }
        sc.close();
    }

    public static int calculateRounds(int k, int m) {
        if (m <= 1) return 1;
        int marked = 1;
        int rounds = 1;
        while (marked < m) {
            int unmarked = m - marked;
            int newMarked = Math.min(marked * k, unmarked);
            marked += newMarked;
            rounds++;
        }
        return rounds;
    }

}