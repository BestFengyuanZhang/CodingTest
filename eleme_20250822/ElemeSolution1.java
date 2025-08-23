import java.util.*;

public class ElemeSolution1 {
    // Check if a non-negative long is a perfect square
    private static boolean isPerfectSquare(long x) {
        if (x < 0) return false;
        long r = (long) Math.sqrt(x);
        return r * r == x || (r + 1) * (r + 1) == x || (r - 1 >= 0 && (r - 1) * (r - 1) == x);
    }

    public static void main(String[] args) {
        Scanner fs = new Scanner(System.in);
        StringBuilder out = new StringBuilder();
        int T;
        if (!fs.hasNext()) return;
        T = fs.nextInt();
        for (int t = 0; t < T; t++) {
            long n = fs.nextLong();
            // Condition 1: n itself is a perfect square
            if (!isPerfectSquare(n)) {
                out.append("NO\n");
                continue;
            }
            // Compute digit sum and digit product
            long tmp = n;
            long sum = 0;
            long prod = 1;
            if (tmp == 0) { // not possible since n>=1, but keep for completeness
                prod = 0;
            } else {
                while (tmp > 0) {
                    int d = (int)(tmp % 10);
                    sum += d;
                    if (prod != 0) { // once zero, stays zero
                        prod *= d;
                    }
                    tmp /= 10;
                }
            }
            // Condition 2 and 3: sum and product are perfect squares
            boolean ok = isPerfectSquare(sum) && isPerfectSquare(prod);
            out.append(ok ? "YES\n" : "NO\n");
        }
        System.out.print(out);
    }

}
