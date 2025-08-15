package base.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * 描述
 */

public class AtomicIntegerDemo {
    static AtomicInteger i = new AtomicInteger();
    public static class AddThread implements Runnable {
        public void run()
        {
            for (int k = 0; k < 10000; k++)
                i.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new AddThread());
        }
        //启动线程并进入就绪状态
        for (int i = 0; i < 10; i++) {
            ts[i].start();
        }
        //让当前线程等待调用 join() 的线程执行完毕后再继续执行
        for (int i = 0; i < 10; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(i);
    }
}
