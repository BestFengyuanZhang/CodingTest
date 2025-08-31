import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 最终的竞赛级 Java 解决方案
 */
public class AliSolution2 {

    /**
     * 主函数，程序入口
     */
    public static void main(String[] args) throws IOException {
        // 使用高效的IO
        FastReader sc = new FastReader();
        PrintWriter out = new PrintWriter(System.out);

        // 根据题目描述，每个输入文件只有一个测试用例，因此直接调用solve
        solve(sc, out);

        // 刷新输出缓冲区
        out.flush();
    }

    /**
     * 解决问题的核心逻辑
     * @param sc FastReader实例用于输入
     * @param out PrintWriter实例用于输出
     */
    private static void solve(FastReader sc, PrintWriter out) throws IOException {
        // --- 1. 输入处理 ---
        int n = sc.nextInt();
        long L = sc.nextLong();
        long R = sc.nextLong();

        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }

        // --- 2. 预处理：计算前缀和 ---
        // p[i] 存储 a[0] 到 a[i-1] 的和
        long[] p = new long[n + 1];
        p[0] = 0;
        for (int i = 0; i < n; i++) {
            p[i + 1] = p[i] + a[i];
        }
        long totalSum = p[n];

        // --- 3. 计算所有可能的收益 ---
        // gainL[x]: 将前 x 个元素变为 L 带来的收益
        long[] gainL = new long[n + 1];
        gainL[0] = 0; // 不操作，收益为0
        for (int x = 1; x <= n; x++) {
            gainL[x] = (long)x * L - p[x];
        }

        // gainR[y]: 将后 y 个元素变为 R 带来的收益
        long[] gainR = new long[n + 1];
        gainR[0] = 0; // 不操作，收益为0
        for (int y = 1; y <= n; y++) {
            // p[n] - p[n-y] 是原数组后 y 个元素的和
            gainR[y] = (long)y * R - (p[n] - p[n - y]);
        }

        // --- 4. 预处理：计算后缀收益的前缀最大值 ---
        // maxGainRSoFar[k] = max(gainR[0], gainR[1], ..., gainR[k])
        long[] maxGainRSoFar = new long[n + 1];
        maxGainRSoFar[0] = gainR[0];
        for (int k = 1; k <= n; k++) {
            maxGainRSoFar[k] = Math.max(maxGainRSoFar[k - 1], gainR[k]);
        }

        // --- 5. 主循环：枚举前缀长度x，O(1)查找最优后缀y ---
        // 初始最大总收益为0 (对应不进行任何操作)
        long maxTotalGain = 0;

        // 遍历所有可能的前缀操作长度 x (x=0 表示不操作前缀)
        for (int x = 0; x <= n; x++) {
            // 对于给定的前缀长度x，后缀长度y最多为 n-x
            int y_max_len = n - x;

            // 从预处理的数组中 O(1) 找到 [0, y_max_len] 范围内的最大后缀收益
            long bestGainR = maxGainRSoFar[y_max_len];

            // 更新最大总收益
            maxTotalGain = Math.max(maxTotalGain, gainL[x] + bestGainR);
        }

        // --- 6. 输出最终结果 ---
        // 最终答案 = 原始总和 + 最大总收益
        out.println(totalSum + maxTotalGain);
    }

    /**
     * 高效读写模板
     */
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

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

        String nextLine() throws IOException {
            return br.readLine();
        }
    }
}