package week0401;

import java.util.concurrent.CyclicBarrier;

/**
 * 第八种方式，使用CyclicBarrier 来进行控制
 * 但是这个从主线程获得结果，而是子线程回调获得的结果
 */
public class Homework0308 {
    static FiboResultSupport resultSupport = new FiboResultSupport();
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int threadCount = 1;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                System.out.println("回调>>"+Thread.currentThread().getName());
                System.out.println("回调>>线程组执行结束，获得异步结果"+resultSupport.result);
            }
        });
        new Thread(new readNum(threadCount, cyclicBarrier)).start();

        long end = System.currentTimeMillis();
        System.out.println("耗时"+(end-start)+"ms");

    }
    static class readNum  implements Runnable{
        private int id;
        private CyclicBarrier cyc;
        public readNum(int id,CyclicBarrier cyc){
            this.id = id;
            this.cyc = cyc;
        }
        @Override
        public void run() {
            synchronized (this){
                System.out.println("id:"+id+","+Thread.currentThread().getName());
                try {
                    FiboCall fiboCall = new FiboCall();
                    resultSupport.result = fiboCall.sum();
                    System.out.println("线程组任务" + id + "结束，其他任务继续");
                    cyc.await();   // 注意跟CountDownLatch不同，这里在子线程await
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
