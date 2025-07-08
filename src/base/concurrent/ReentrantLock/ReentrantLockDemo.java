package base.concurrent.ReentrantLock;

/**
 * @Author
 * @Time 2025/7/7 18:53
 * @Desciption 可重入锁案例
 * 可重入锁需要手动释放
 * @Version
 */

public class ReentrantLockDemo implements Runnable{
    public static java.util.concurrent.locks.ReentrantLock lock = new java.util.concurrent.locks.ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            try {
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo r1 = new ReentrantLockDemo();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
