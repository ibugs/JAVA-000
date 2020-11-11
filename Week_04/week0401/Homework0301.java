package week0401;

/**
 * @author wanghao
 * 2020-11-11 13:38
 */
public class Homework0301 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int result = sum();

		System.out.println("异步执行的结果为：" + result);
		long end = System.currentTimeMillis();
		System.out.println("使用时间：" + (end - start) + "ms");
	}

	public static int sum() {
		return fibo(36);
	}

	private static int fibo(int a) {
		if (a < 2) {
			return 1;
		}
		return fibo(a - 1) + fibo(a - 2);
	}
}
