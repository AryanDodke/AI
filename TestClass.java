import java.io.*;

public class TestClass {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);

        int A = Integer.parseInt(br.readLine().trim());
        int B = Integer.parseInt(br.readLine().trim());
        int C = Integer.parseInt(br.readLine().trim());

        int out_ = ValidCombinations(A, B, C);
        System.out.println(out_);

        wr.close();
        br.close();
    }

    static int ValidCombinations(int A, int B, int C) {
        if (C == 0) {
            return B;
        }

        long result = 0;
        long comb = nCrModPFermat(A - 1, C, MOD);
        result = (comb * B) % MOD;
        result = (result * power(B - 1, C, MOD)) % MOD;

        return (int) result;
    }

    private static final int MOD = 998244353;

    private static long power(long x, long y, long p) {
        long res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) != 0)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }

    private static long modInverse(long n, long p) {
        return power(n, p - 2, p);
    }

    private static long nCrModPFermat(long n, long r, long p) {
        if (r == 0)
            return 1;

        long[] fac = new long[(int) n + 1];
        fac[0] = 1;

        for (int i = 1; i <= n; i++)
            fac[i] = fac[i - 1] * i % p;

        return (fac[(int) n] * modInverse(fac[(int) r], p) % p * modInverse(fac[(int) (n - r)], p) % p) % p;
    }
}