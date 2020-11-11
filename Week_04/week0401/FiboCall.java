package week0401;

public class FiboCall {
    public synchronized int sum() {
        return fibo(36);
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
