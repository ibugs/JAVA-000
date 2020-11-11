package week0401;

import java.util.concurrent.CountDownLatch;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 第二种方法，使用 CountDownLatch
 */
public class Homework0302 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new readNum(1, countDownLatch)).start();
        countDownLatch.await();
        System.out.println("==>各个子线程执行结束。。。。");
        System.out.println("==>主线程执行结束。。。。");
    }

    static class readNum implements Runnable {
        private int id;
        private CountDownLatch latch;

        public readNum(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            synchronized (this) {
                FiboCall fiboCall = new FiboCall();
                System.out.println("id:" + id + "," + Thread.currentThread().getName());
                System.out.println("线程组任务" + id + "结束，其他任务继续，算出来的Fibo结果为" + fiboCall.sum());
                latch.countDown();
            }
        }
    }
}
