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
        Map<Long, Long> cnt = new HashMap<>();
        long mx = 0;
        for (int i = 0; i < n; i++) {
            long a = in.nextLong();
            cnt.put(a, cnt.getOrDefault(a, 0L) + 1);
            mx = Math.max(mx, a);
        }

        // 处理m个查询
        for (int q = 0; q < m; q++) {
            long x = in.nextLong();
            long res = 0;
            Set<Long> visited = new HashSet<>();

            // 找x的所有因子（x能被这些数整除）
            for (long i = 1; i * i <= x; i++) {
                if (x % i == 0) {
                    if (cnt.containsKey(i) && !visited.contains(i)) {
                        res += cnt.get(i);
                        visited.add(i);
                    }
                    long other = x / i;
                    if (i != other && cnt.containsKey(other) && !visited.contains(other)) {
                        res += cnt.get(other);
                        visited.add(other);
                    }
                }
            }

            // 找x的倍数（这些数能被x整除）
            if (x <= mx) {
                for (long mul = x; mul <= mx; mul += x) {
                    if (cnt.containsKey(mul) && !visited.contains(mul)) {
                        res += cnt.get(mul);
                        visited.add(mul);
                    }
                }
            }

            out.append(res).append('\n');
        }

        PrintWriter  pw = new PrintWriter(System.out);
        pw.print(out);
        pw.flush();
    }
}