package thread01;

public class TestP2C2 {
    public static void main(String[] args) {
        ComputerCounter computerCounter = new ComputerCounter();
        computerCounter.counter = 0;
        ThreadProduct2 tp21 = new ThreadProduct2(computerCounter);
        ThreadConsumer2 tc21 = new ThreadConsumer2(computerCounter);

        Thread t1 = new Thread(tp21, "生产者1");
        Thread t2 = new Thread(tc21, "消费者1");
        Thread t3 = new Thread(tc21, "消费者2");

        t1.start();
        t2.start();
        t3.start();
    }
}
