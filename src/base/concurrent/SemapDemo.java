package base.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author
 * @Time 2025/7/9 11:18
 * @Desciption 这是一个信号量的使用案例
 * new Semaphore(5)申明了同时可以有5个线程进入代码段7-9行
 * 申请信号量使用acquire()操作，离开时，务必使用release()操作
 * 如果申请了但没有释放，会导致可用信号量越来越少
 *
 * 如果注释了semp.acquire()，没有5个信号量的限制，20个任务会直接执行完成
 * 如果注释了第一个semp.release()，由于JVM的介入，会执行finally块的释放操作，这样程序可以正确执行，但是不提倡
 * 如果把第二个semp.release()也注释了，则信号量会一直占用，无法释放，程序会卡死
 * @Version
 */

public class SemapDemo implements Runnable{
    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ":done!");
            //semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //semp.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemapDemo demo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            exec.submit(demo);
        }
    }
}