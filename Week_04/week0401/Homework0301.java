package week0401;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 第一种方法
 * @author wanghao
 * 2020-11-11 13:38
 */
public class Homework0301 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		FiboCall fiboCall = new FiboCall();
		int result = fiboCall.sum();
		// 24157817
		System.out.println("异步执行的结果为：" + result);
		long end = System.currentTimeMillis();
		System.out.println("使用时间：" + (end - start) + "ms");
	}

}
