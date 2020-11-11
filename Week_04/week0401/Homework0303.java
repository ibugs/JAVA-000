package week0401;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程
 * 写出你的方法，越多越好，提交到github。
 *
 * 第三种方法，使用线程池，接受返回参数的方式执行，最后线程池shutdown
 */
public class Homework0303 {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> future = executorService.submit(() -> {
            FiboCall fiboCall = new FiboCall();
            return fiboCall.sum();
        });
        System.out.println("异步执行的结果为：" + future.get());
        long end = System.currentTimeMillis();
        System.out.println("使用时间：" + (end - start) + "ms");
        executorService.shutdown();
    }
}
