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

    static final long INF = Long.MIN_VALUE / 2;

    public static void main(String[] args) throws Exception {
        FastReader in = new FastReader();

        int n = in.nextInt();
        int k = in.nextInt();
        long[] a = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextLong();
        }

        // 使用记忆化搜索
        Long[][] memo = new Long[n + 1][k + 1];

        System.out.println(solve(n, k, a, memo));
    }

    static long solve(int i, int j, long[] a, Long[][] memo) {
        // 边界条件
        if (j == 0) return i == 0 ? 0 : INF;
        if (i < j) return INF;
        if (memo[i][j] != null) return memo[i][j];

        long res = INF;

        // 枚举最后一个军团的起始位置
        // 最后一个军团从位置t开始到位置i结束
        for (int t = j; t <= i; t++) {
            // 计算[t, i]区间的最小值
            long minVal = Long.MAX_VALUE;
            for (int p = t; p <= i; p++) {
                minVal = Math.min(minVal, a[p]);
            }

            long prev = solve(t - 1, j - 1, a, memo);
            if (prev != INF) {
                res = Math.max(res, prev + minVal);
            }
        }

        return memo[i][j] = res;
    }
}