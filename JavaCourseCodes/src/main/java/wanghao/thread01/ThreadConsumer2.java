package thread01;

public class ThreadConsumer2 implements Runnable {
    private ComputerCounter computerCounter;
    public ThreadConsumer2(ComputerCounter computerCounter){
        this.computerCounter = computerCounter;
    }
    public void run() {
        synchronized (computerCounter.lock){
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::CONSUMER:::" + computerCounter.counter);
                if (computerCounter.counter <= 0) {
                    System.out.println(Thread.currentThread().getName() + "，仓库已经消费空了");
                    try {
                        // 重点：这里必须调用的是被锁定synchronized对象的wait()方法
                        // 如果只调用 wait() 方法时，那么就会抛出来 IllegalMonitorStateException
                        // 说【当前线程不是拥有者】
                        computerCounter.lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                } else {
                    computerCounter.counter--;
                    computerCounter.lock.notifyAll();
                }
            }
        }
    }
}
