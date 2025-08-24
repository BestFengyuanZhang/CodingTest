import java.io.*;
import java.util.*;

public class RedBookSolution1 {
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
    }

    public static void main(String[] args) throws Exception {
        FastReader in = new FastReader();
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int ans = 0;
        // 直接枚举2-100，简单直接
        for (int g = 2; g <= 100; g++) {
            int p1 = 0, p2 = 0;
            for (int i = 0; i < n; i++) {
                int curr = p1;
                if (a[i] % g == 0) {
                    curr = Math.max(curr, p2 + 1);
                }
                p2 = p1;
                p1 = curr;
            }
            ans = Math.max(ans, p1);
        }
        System.out.println(ans);
    }
}