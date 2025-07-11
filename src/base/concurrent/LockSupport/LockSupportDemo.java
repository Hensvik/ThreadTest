package base.concurrent.LockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author
 * @Time 2025/7/11 11:02
 * @Desciption LockSupport的案例
 * LockSupport可以在线程内任意位置让线程阻塞，和Thread.suspend()相比，它弥补了由于resume()在前发生，导致线程无法继续执行的情况。
 * 和Object.wait()相比，它不需要先获得的某个对象的锁，也不会抛出InterrupedException。
 * LockSupport的静态方法park()可以阻塞当前线程，类似的还有parkNanos(),parkUntil()等
 *
 *
 * @Version
 */

public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name)
        {
            super.setName(name);
        }

        @Override
        public void run()
        {
            synchronized (u)
            {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*
         * park()和unpark()方法相比原来的suspend()和resume()方法，有如下特点：
         * 这段代码却始终都能正常的结束
         *
         * 这是因为LockSupport类使用类似信号量的机制。它为每一个线程准备了一个许可，如果许可可用，那么park()函数会立即返回，并且消费这个许可(即将许可变为不可用)
         * 如果许可不可用，那么park()函数会阻塞当前线程，而unpark()则使得一个许可变为可用(但是和信号量不同的是，许可不能增加，你不可能拥有超过一个许可，它永远只有一个)
         *
         * 这个特点使得：即使unpark()操作发生在park()之前，它也可以使下一次的park()操作立即返回，这也是上述代码可以顺利结束的主要原因
         * 同时，处于park()挂起状态的线程不会像suspend()一样给出一个令人费解的Runnable的状态，它会非常明确地给出一个WAITING状态，甚至还会标注是park()引起的
         */

        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
