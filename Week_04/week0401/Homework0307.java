package week0401;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程
 * 写出你的方法，越多越好，提交到github。
 *
 * 第七种方式，使用join的方式
 */
public class Homework0307 {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        FiboResultSupport fiboResultSupport = new FiboResultSupport();
        Homework0307Thread ht0307 = new Homework0307Thread(fiboResultSupport);
        Thread t1 = new Thread(ht0307);
        t1.start();

        for(int i =0; i < 100; i ++){
           if(i == 33){
              t1.join();
           }
            System.out.println(Thread.currentThread().getName()+"【"+i+"】");
        }
        System.out.println("获得了异步结果"+fiboResultSupport.result);
        long end = System.currentTimeMillis();
        System.out.println("耗时"+(end - start)+"ms");
    }
}

class Homework0307Thread implements Runnable {
    private FiboResultSupport threadResult;
    public Homework0307Thread(FiboResultSupport resultSupport){
       this.threadResult = resultSupport;
    }

    @Override
    public void run() {
        FiboCall fiboCall = new FiboCall();
        threadResult.result = fiboCall.sum();
        System.out.println("子线程获得了结果"+threadResult.result);
    }
}
