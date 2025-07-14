package base.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池拓展类
 * shutdown()方法会等待所有任务执行完成后再关闭线程池，可以理解shutdown()方法是发送了一个关闭信号，但是在shutdown()方法执行后，这个线程就不能再接受新的任务
 */

public class ExtThreadPool {
    public static class MyTask implements Runnable {
        public String name;
        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("正在执行" + ":Thread ID:" + Thread.currentThread().getId() + ", Task Name=" + name);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()){

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行:" + ((MyTask)r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成:" + ((MyTask)r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask myTask = new MyTask("Task" + i);
            es.execute(myTask);
            Thread.sleep(10);
        }
        es.shutdown();
    }
}
