import java.io.*;
import java.util.*;

/**
 * Main class to solve the problem.
 * The problem asks for the number of sequences of length n, with elements in [0, x],
 * such that the bitwise OR of all elements is exactly K.
 *
 * The solution uses the Principle of Inclusion-Exclusion combined with Sum-over-Subsets (SOS) DP.
 *
 * 1. Let dp[mask] be the number of sequences whose bitwise OR is exactly `mask`. We want to find dp[K].
 * <p>
 * 2. Let f[mask] be the number of sequences whose bitwise OR is a submask of `mask`.
 *    This means for every element `a_i` in the sequence, `a_i` must also be a submask of `mask`.
 *    f[mask] = (Number of valid choices for one element)^n.
 *    A valid choice `v` must satisfy `0 <= v <= x` and `v` is a submask of `mask`.
 *    Let N[mask] be this number of choices. Then f[mask] = (N[mask])^n.
 *
 * 3. The relationship between f and dp is given by: f[mask] = sum_{sub is a submask of mask} dp[sub].
 *    This is a standard sum-over-subsets transform.
 *
 * 4. By the inverse transform (or inclusion-exclusion), we have:
 *    dp[mask] = sum_{sub is a submask of mask} (-1)^(|mask| - |sub|) * f[sub].
 *
 * The overall algorithm is:
 * a. Calculate N[mask] for all masks. This can be done efficiently using SOS DP.
 *    - Initialize an array `counts[v] = 1` if `v <= x`, else `0`.
 *    - N[mask] = sum_{sub is a submask of mask} counts[sub]. This is a forward SOS DP.
 * b. Calculate f[mask] = power(N[mask], n, MOD) for all masks.
 * c. Calculate dp[mask] from f[mask] using an inverse SOS DP.
 * d. The answer is dp[K].
 */
public class AliSolution3 {

    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        solve(sc, out);
        out.flush();
    }

    private static void solve(FastReader sc, PrintWriter out) throws IOException {
        long n = sc.nextLong();
        int x = sc.nextInt();
        int k = sc.nextInt();

        // Since x and k are at most 1000, they fit within 10 bits (2^10 = 1024).
        final int MAX_BITS = 10;
        final int MAX_VAL = 1 << MAX_BITS; // 1024

        // Step a: Calculate N[mask] for all masks up to MAX_VAL.
        // N[mask] = number of integers v such that 0 <= v <= x AND v is a submask of mask.
        long[] N = new long[MAX_VAL];
        for (int i = 0; i <= x; i++) {
            N[i] = 1;
        }

        // Apply Sum-over-Subsets DP to calculate N[mask] for all masks.
        // After this, N[mask] = sum_{sub is a submask of mask, sub <= x} 1.
        for (int i = 0; i < MAX_BITS; i++) {
            for (int mask = 0; mask < MAX_VAL; mask++) {
                // If the i-th bit of the mask is set
                if (((mask >> i) & 1) == 1) {
                    // Add the count from the submask where the i-th bit is not set.
                    N[mask] = (N[mask] + N[mask ^ (1 << i)]);
                }
            }
        }

        // Step b: Calculate f[mask] = (N[mask])^n % MOD.
        long[] f = new long[MAX_VAL];
        for (int mask = 0; mask < MAX_VAL; mask++) {
            f[mask] = power(N[mask], n, MOD);
        }

        // Step c: Apply inverse SOS DP to get dp[mask] from f[mask].
        // dp[mask] = sum_{sub is a submask of mask} (-1)^(|mask|-|sub|) * f[sub]
        for (int i = 0; i < MAX_BITS; i++) {
            for (int mask = 0; mask < MAX_VAL; mask++) {
                // If the i-th bit of the mask is set
                if (((mask >> i) & 1) == 1) {
                    // Subtract the value from the submask where the i-th bit is not set.
                    f[mask] = (f[mask] - f[mask ^ (1 << i)] + MOD) % MOD;
                }
            }
        }

        // Step d: The answer is the final value of f[k], which now holds dp[k].
        out.println(f[k]);
    }

    /**
     * Modular exponentiation to compute (base^exp) % mod.
     * Handles large exponents efficiently.
     */
    private static long power(long base, long exp, int mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) {
                res = (res * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        return res;
    }

    // --- Fast I/O Template ---
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
}