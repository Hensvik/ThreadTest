package base.threadpool;

import java.util.concurrent.*;

/**
 * 自定义拒绝策略
 */
public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread()+ ":Thread ID:" + Thread.currentThread().getId());
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10), Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is discard");
                    }
                });
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            es.submit(myTask);
            Thread.sleep(10);
        }
    }
}
