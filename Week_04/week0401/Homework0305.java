package week0401;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 第五种方式，在第四种的方式上改进一下，使用乐观锁的方式来处理
 */
public class Homework0305 {
    static Integer singal = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FiboResultSupport result = new FiboResultSupport();
        while (true) {
            if (singal == 0) {
                System.out.println("signal为0的时候，子线程开始执行" + Thread.currentThread().getName());
                singal += 1;
                Homework0305Thread ht1 = new Homework0305Thread(result);
                Thread t1 = new Thread(ht1);
                t1.start();
            }
            System.out.println("now signal =>"+singal);
            if (singal == 2) {
                System.out.println("异步执行的结果为：" + result.result);
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("使用时间：" + (end - start) + "ms");
    }

    static class Homework0305Thread implements Runnable {
        private FiboResultSupport support;

        public Homework0305Thread(FiboResultSupport support) {
            this.support = support;
        }

        @Override
        public void run() {
            FiboCall fiboCall = new FiboCall();
            support.result = fiboCall.sum();
            System.out.println("子线程" + Thread.currentThread().getName() + "计算完毕");
            singal += 1;
            System.out.println("this signal =>"+singal);
        }
    }
}

