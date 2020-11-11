package thread01;

/***
 * 重点：
 * 1. 信号量，这里的共享计数
 * 2. 生产/消费完毕，一定要释放锁
 * 3. 要有notifyAll()的机制，没有唤醒其他线程就只能死等着
 */
public class Computer {
    private static final int MAX_VALUE = 10;
    // 重点一，共享计数
    private int counter = 0;

    public synchronized void product() throws InterruptedException {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":::PRODUCT:::" + counter);
            if (counter >= MAX_VALUE) {
                System.out.println(Thread.currentThread().getName() + "，仓库已经生产满了");
                // 重点二，生产/消费完毕，一定要释放锁(如果不释放，其他方法是进不来的)
                wait();
            } else {
                counter++;
                // 重点三，唤醒其他睡觉的线程(其他线程也调用了wait方法，必须使用notify才能够真正的唤醒)
                notifyAll();
            }
        }
    }

    public synchronized void consumer() throws InterruptedException {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":::CONSUMER:::" + counter);
            if (counter <= 0) {
                System.out.println(Thread.currentThread().getName() + "，仓库已经消费空了");
                wait();
            } else {
                counter--;
                notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        Computer computer = new Computer();
        ThreadProducter p1 = new ThreadProducter(computer);
        ThreadConsumer c1 = new ThreadConsumer(computer);
        ThreadConsumer c2 = new ThreadConsumer(computer);

        Thread t1 = new Thread(p1, "生产者1");
        Thread t2 = new Thread(c1, "消费者1");
        Thread t3 = new Thread(c2, "消费者2");

        t1.start();
        t2.start();
        t3.start();
    }
}

class ThreadProducter implements Runnable {
    private Computer computer;

    public ThreadProducter(Computer computer) {
        this.computer = computer;
    }

    public void run() {
        try {
            computer.product();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadConsumer implements Runnable {
    private Computer computer;

    public ThreadConsumer(Computer computer) {
        this.computer = computer;
    }

    public void run() {
        try {
            computer.consumer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
