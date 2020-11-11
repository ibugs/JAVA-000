package week0401;

/***
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 第四种方法，启动子线程，然后主线程sleep，子线程执行完毕之后，主线程也sleep完成了，获得最终结果
 */
public class Homework0304 {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ResultSupport result = new ResultSupport();
        for (int i = 0; i < 10000000; i++) {
            if (i == 101) {
                System.out.println("到了101，主线程wait， " + Thread.currentThread().getName() + "wait");
                Homework0304Thread ht1 = new Homework0304Thread(result);
                Thread t1 = new Thread(ht1);
                t1.start();
                Thread.sleep(1000);
            }
        }
        System.out.println("异步执行的结果为：" + result.result);
        long end = System.currentTimeMillis();
        System.out.println("使用时间：" + (end - start) + "ms");
    }
}

class ResultSupport {
    Integer result = 0;
}

class Homework0304Thread implements Runnable {
    private ResultSupport resultSupport;

    public Homework0304Thread(ResultSupport resultSupport) {
        this.resultSupport = resultSupport;
    }

    @Override
    public void run() {
        FiboCall fiboCall = new FiboCall();
        this.resultSupport.result = fiboCall.sum();
        System.out.println("进入到了子线程" + Thread.currentThread().getName() + "fibo已经执行完毕了");
    }
}
