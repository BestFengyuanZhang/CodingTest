import java.io.*;
import java.util.*;

public class RedBookSolution3 {
    static class FastReader {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastReader in = new FastReader();
        StringBuilder out = new StringBuilder();

        int n = in.nextInt();
        int k = in.nextInt();
        long[] a = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextLong();
        }

        // 使用记忆化搜索可能更直观
        Long[][] memo = new Long[n + 1][k + 1];

        long ans = solve(n, k, a, memo);
        out.append(ans).append('\n');

        PrintWriter pw = new PrintWriter(System.out);
        pw.print(out);
        pw.close();
    }

    static long solve(int i, int j, long[] a, Long[][] memo) {
        if (j == 0) return i == 0 ? 0 : Long.MIN_VALUE / 2;
        if (i < j) return Long.MIN_VALUE / 2;
        if (memo[i][j] != null) return memo[i][j];

        long res = Long.MIN_VALUE / 2;
        long minVal = Long.MAX_VALUE;

        // 枚举最后一个军团
        for (int t = i; t >= j; t--) {
            minVal = Math.min(minVal, a[t]);
            res = Math.max(res, solve(t - 1, j - 1, a, memo) + minVal);
        }

        return memo[i][j] = res;
    }
}