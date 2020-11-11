package week0401;

/***
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程
 * 写出你的方法，越多越好，提交到github。
 *
 * 第六种方式，使用volatile来控制
 */
public class Homework0306 {
    int result = 0;
    volatile boolean isCall = false;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Homework0306 homework0306 = new Homework0306();
        Thread t1 = new Thread(() -> {
            homework0306.writer();
        });
        t1.start();

        while(true){
            if(!homework0306.isCall){
                //System.out.println(Thread.currentThread().getName()+"还没有获得结果");
            }else{
                break;
            }
        }
        System.out.println("异步获得结果"+homework0306.result);
        long end = System.currentTimeMillis();
        System.out.println("耗时"+(end - start)+"ms");
    }

    public void writer() {
        FiboCall fiboCall = new FiboCall();
        result = fiboCall.sum();
        isCall = true;
    }
}
