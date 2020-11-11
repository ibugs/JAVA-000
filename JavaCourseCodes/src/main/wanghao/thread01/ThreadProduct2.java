package thread01;

public class ThreadProduct2 implements Runnable{
    private ComputerCounter computerCounter;
    public ThreadProduct2(ComputerCounter computerCounter){
        this.computerCounter = computerCounter;
    }
    public void run() {
        // 如果这里锁的是 computerCounter 那么下面调用的时候
        // 也得是 computerCounter.wait() computerCounter.notifyAll() 因为标记
        // 的是同一个对象上面的对象锁
        synchronized (computerCounter.lock){
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::PRODUCT:::" + computerCounter.counter);
                if (computerCounter.counter >= computerCounter.MAX_VALUE) {
                    System.out.println(Thread.currentThread().getName() + "，仓库已经生产满了");
                    // 重点二，生产/消费完毕，一定要释放锁(如果不释放，其他方法是进不来的)
                    try {
                        computerCounter.lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                } else {
                    computerCounter.counter++;
                    // 重点三，唤醒其他睡觉的线程(其他线程也调用了wait方法，必须使用notify才能够真正的唤醒)
                    computerCounter.lock.notifyAll();
                }
            }
        }
    }
}
