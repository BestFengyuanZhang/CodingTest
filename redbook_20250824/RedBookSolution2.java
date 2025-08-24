import java.io.*;
import java.util.*;

public class RedBookSolution2 {
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
        int m = in.nextInt();

        // 统计每个值出现的次数
        Map<Long, Integer> cnt = new HashMap<>();
        long mx = 0;
        for (int i = 0; i < n; i++) {
            long a = in.nextLong();
            cnt.put(a, cnt.getOrDefault(a, 0) + 1);
            mx = Math.max(mx, a);
        }

        // 处理m个查询
        for (int q = 0; q < m; q++) {
            long x = in.nextLong();
            Set<Long> visited = new HashSet<>();
            int res = 0;

            // 找x的所有因子（包括1和x本身）
            for (long i = 1; i * i <= x; i++) {
                if (x % i == 0) {
                    if (!visited.contains(i)) {
                        visited.add(i);
                        res += cnt.getOrDefault(i, 0);
                    }
                    long other = x / i;
                    if (i != other && !visited.contains(other)) {
                        visited.add(other);
                        res += cnt.getOrDefault(other, 0);
                    }
                }
            }

            // 找x的倍数（不包括x本身，因为已经在因子中处理过了）
            for (long mul = 2 * x; mul <= mx; mul += x) {
                if (!visited.contains(mul)) {
                    visited.add(mul);
                    res += cnt.getOrDefault(mul, 0);
                }
            }

            out.append(res).append('\n');
        }

        System.out.print(out);
    }
}