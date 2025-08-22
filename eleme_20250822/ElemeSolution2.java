import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

public class ElemeSolution2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null) return null;
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }

    public static void main(String[] args) throws Exception {
        String s = next();
        if (s == null) return;
        int n = Integer.parseInt(s);
        final long NEG = Long.MIN_VALUE / 4;
        long[] dp = new long[10];
        Arrays.fill(dp, NEG);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            long a = Long.parseLong(Objects.requireNonNull(next()));
            long[] ndp = new long[10];
            Arrays.fill(ndp, NEG);

            for (int r = 0; r < 10; r++) {
                long cur = dp[r];
                if (cur == NEG) continue;

                long vSkip = cur + i;
                if (vSkip > ndp[r]) ndp[r] = vSkip;

                int r2 = r + 1;
                if (r2 == 10) r2 = 0;
                long reward = a * (1 + r2);
                long vBeat = cur + reward;
                if (vBeat > ndp[r2]) ndp[r2] = vBeat;
            }
            dp = ndp;
        }

        long ans = Long.MIN_VALUE;
        for (int r = 0; r < 10; r++) {
            if (dp[r] > ans) ans = dp[r];
        }
        System.out.println(ans);
    }
}
