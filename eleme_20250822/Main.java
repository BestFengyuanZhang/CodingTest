import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        int T = sc.nextInt();
        while (T-- > 0) {
            solve(sc, out);
        }
        out.flush();
    }

    private static void solve(FastReader sc, PrintWriter out) throws IOException {
        // 读取矩阵大小
        int n = sc.nextInt();

        // 读取n×n矩阵
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // 特殊情况：n=1
        if (n == 1) {
            out.println(grid[0][0]);
            return;
        }

        // 动态规划数组
        // dp[i][j]表示从(0,0)到(i,j)的最大路径和
        int[][] dp = new int[n][n];

        // 初始化起点
        dp[0][0] = grid[0][0];

        // 初始化第一行
        // 第一行只能一直向右走
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }

        // 初始化第一列
        // 第一列只能一直向下走
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }

        // 填充dp表格
        // 对于位置(i,j)，可以从(i-1,j)向下走到达，或从(i,j-1)向右走到达
        // 选择两者中路径和更大的那个
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                // 从上方来的路径和：dp[i-1][j] + grid[i][j]
                // 从左方来的路径和：dp[i][j-1] + grid[i][j]
                // 取两者最大值
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }

        // 输出从(0,0)到(n-1,n-1)的最大路径和
        out.println(dp[n-1][n-1]);
    }

    // Fast I/O template
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

        String nextLine() throws IOException {
            return br.readLine();
        }
    }
}