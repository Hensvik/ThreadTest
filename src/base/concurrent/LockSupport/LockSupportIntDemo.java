package base.concurrent.LockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author
 * @Time 2025/7/11 11:22
 * @Desciption
 * @Version
 */

public class LockSupportIntDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println("被中断了");
                }
            }
            System.out.println(getName() + "结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();

        //下面这行代码中断了处于park()状态的t1，之后，t1可以马上相应这个中断并且返回。之后在外面等待的t2才可以进入临界区，并最终将LockSupport.unpark(2)操作使其运行结束
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
