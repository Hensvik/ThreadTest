package base.concurrent.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author
 * @Time 2025/7/8 19:20
 * @Desciption 公平锁的实现
 * @Version
 */
public class FairLock implements Runnable{

    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while(true){
            try{
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " get the lock");
            }finally{
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock,"Thread_t1");
        Thread t2 = new Thread(fairLock,"Thread_t2");
        t1.start();
        t2.start();
    }

}
