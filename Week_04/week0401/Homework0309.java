package week0401;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程
 * 写出你的方法，越多越好，提交到github。
 *
 * 第九种方式，使用生产者消费者
 */
public class Homework0309 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        FiboResultSupport fiboResultSupport = new FiboResultSupport();

        Product p = new Product(fiboResultSupport);
        Customer c = new Customer(fiboResultSupport);

        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        t1.start();t2.start();
        // 1. 加上这两步就相当于从主线程获得结果
        // 2. 去掉这两步就相当于从子线程获得结果
        t1.join();t2.join();
        long end = System.currentTimeMillis();

        System.out.println("获得异步结果"+fiboResultSupport.result);
        System.out.println("耗时"+(end - start)+"ms");
    }
}

class Product implements Runnable{
    private FiboResultSupport resultSupport;

    public Product (FiboResultSupport resultSupport){
       this.resultSupport = resultSupport;
    }

    @Override
    public void run() {
        synchronized (resultSupport) {
            try {
                while (true) {
                    if (resultSupport.count == 0) {
                        resultSupport.created = true;
                        resultSupport.count = 1;
                        System.out.println("生产者生产完毕");
                        resultSupport.notifyAll();
                        break;
                    } else {
                        System.out.println("生产者再等待结果");
                        resultSupport.wait();
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable {
    private FiboResultSupport fiboResultSupport;

    public Customer(FiboResultSupport fiboResultSupport){
       this.fiboResultSupport = fiboResultSupport;
    }

    @Override
    public void run() {
       synchronized (fiboResultSupport) {
           while (true) {
               try {
                   if (fiboResultSupport.count >= 1) {
                       FiboCall fiboCall = new FiboCall();
                       fiboResultSupport.result = fiboCall.sum();
                       //fiboResultSupport.count = 0;
                       System.out.println("消费者已经计算完毕，结果为"+fiboResultSupport.result);
                       fiboResultSupport.notifyAll();
                       break;
                   } else {
                       System.out.println("消费者在等待");
                       fiboResultSupport.wait();
                   }
               }catch (InterruptedException e){
                   e.printStackTrace();
               }
           }
       }
    }
}
