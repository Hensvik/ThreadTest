package base.threadlocal;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description:
 * 这是一个测试ThreadLocal在对多线程下产生随机数在性能上的帮助的测试demo
 */

public class ThreadLocalDemo {
    //规定生成随机数的范围
    public static final int GEN_COUNT = 10000000;
    //规定线程池的线程数量
    public static final int THREAD_COUNT = 4;
    //创建线程池
    static ExecutorService exe = Executors.newFixedThreadPool(THREAD_COUNT);
    //创建随机数生成类
    public static Random rnd = new Random(123);

    public static ThreadLocal<Random> tRnd = new ThreadLocal<>() {
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long>{
        private int mode = 0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        //获取随机数生成器对象
        public Random getRandom() {
            if (mode == 0) {
                return rnd;
            } else if(mode == 1){
                return tRnd.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b = System.currentTimeMillis();
            for (int i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }
            long e = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " spend " + (e - b) + "ms");
            return e - b;
        }
    }

    public static void main(String[] args) {
        Future<Long> [] futs = new Future[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            futs[i] = exe.submit(new RndTask(0));
        }
        long totalTime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                totalTime += futs[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("多线程访问同一个Random实例:"+ totalTime + "ms");

        //ThreadLocal的情况
        for (int i = 0; i < THREAD_COUNT; i++) {
            futs[i] = exe.submit(new RndTask(1));
        }
        totalTime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                totalTime += futs[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("多线程访问ThreadLocal实例:"+ totalTime + "ms");
        exe.shutdown();
    }
}
