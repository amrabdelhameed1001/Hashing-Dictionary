import java.util.ArrayList;
import java.util.Random;

public class PerfectHashing {
    private int[] table;
    private int[] sizes;
    private ArrayList<Integer>[] keys;

    // O(N^2)-space solution
    public PerfectHashing(int[] S) {
        int N = S.length;
        Random random = new Random();
        while (true) {
            table = new int[N * N];
            boolean success = true;
            for (int i = 0; i < N; i++) {
                int hash = random.nextInt(N * N);
                if (table[hash] != 0) {
                    success = false;
                    break;
                }
                table[hash] = S[i];
            }
            if (success) {
                break;
            }
        }
    }

    // O(N)-space solution
    public PerfectHashing(int[] S, int u) {
        int N = S.length;
        Random random = new Random();
        while (true) {
            int M = N * N;
            sizes = new int[N];
            keys = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                keys[i] = new ArrayList<Integer>();
            }
            int[][] h = new int[N][u];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < u; j++) {
                    h[i][j] = random.nextInt(2);
                }
                int hash = 0;
                for (int j = 0; j < u; j++) {
                    hash = (hash << 1) + h[i][j];
                }
                hash %= M;
                sizes[hash / N]++;
                keys[hash / N].add(S[i]);
            }
            boolean success = true;
            for (int i = 0; i < N; i++) {
                if (sizes[i] * sizes[i] > N) {
                    success = false;
                    break;
                }
            }
            if (success) {
                break;
            }
        }
    }

    public int get(int key) {
        if (table != null) {
            int hash = key % (table.length);
            if (table[hash] == key) {
                return hash;
            } else {
                return -1;
            }
        } else {
            int hash = key % (sizes.length);
            int bucket = hash * sizes[hash];
            for (int i = 0; i < sizes[hash]; i++) {
                int index = bucket + i;
                if (keys[hash].get(i) == key) {
                    return index;
                }
            }
            return -1;
        }
    }
}