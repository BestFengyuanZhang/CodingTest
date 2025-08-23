import java.util.*;

public class ElemeSolution2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = scanner.nextInt();
        System.out.println(solve(arr));
        scanner.close();
    }

    public static int solve(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : arr) freq.put(num, freq.getOrDefault(num, 0) + 1);
        List<Integer> values = new ArrayList<>(freq.keySet());
        Collections.sort(values);
        Map<String, Integer> memo = new HashMap<>();
        return dfs(0, 0, values, freq, memo);
    }

    private static int dfs(int idx, int xorSum, List<Integer> values, Map<Integer, Integer> freq, Map<String, Integer> memo) {
        if (idx == values.size()) return 0;
        String key = idx + "," + xorSum;
        if (memo.containsKey(key)) return memo.get(key);
        int val = values.get(idx);
        int result = Integer.MAX_VALUE;
        result = Math.min(result, freq.get(val) + dfs(idx + 1, xorSum, values, freq, memo));
        if (xorSum == 0) {
            int newXor = xorSum;
            if (freq.get(val) % 2 == 1) newXor ^= val;
            result = Math.min(result, dfs(idx + 1, newXor, values, freq, memo));
        }
        memo.put(key, result);
        return result;
    }
}