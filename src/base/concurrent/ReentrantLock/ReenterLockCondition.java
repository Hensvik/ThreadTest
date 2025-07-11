package base.concurrent.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author
 * @Time 2025/7/9 10:57
 * @Desciption Condition对象和wait()和notify()方法的作用大致相同，但是wait和notify方法是和synchronized关键字一起使用，而Condition对象则和重入锁关联
 * @Version
 */

public class ReenterLockCondition implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            //线程获得锁
            lock.lock();
            //线程等待
            condition.await();
            System.out.println("Thread is going on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition r = new ReenterLockCondition();
        Thread t1 = new Thread(r);
        t1.start();
        Thread.sleep(2000);
        //通知线程t1继续执行
        lock.lock();
        //线程唤醒
        condition.signal();
        lock.unlock();
    }
}
