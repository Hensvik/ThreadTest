package base.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 最简单的线程池使用方式
 * 前5个线程和后5个线程正好相差1秒钟
 */

public class ThreadPoolDemo {
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread ID: " + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            es.submit(myTask);
        }

        //线程池关闭代码，如果后面的代码不加，线程池会一直保持着不退出
        es.shutdown(); // 关闭线程池
        try {
            // 等待所有任务完成，最多等待5秒
            if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
                es.shutdownNow(); // 如果任务未完成，尝试强制关闭
            }
        } catch (InterruptedException e) {
            es.shutdownNow(); // 发生异常时也尝试强制关闭
        }
    }
}
