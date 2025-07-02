package base.suspend_resume;

/*
 * Created by Hensvik on 2025/5/9.
 * 当 t1 和 t2 都进入同步块后挂起，它们会持续持有 u 对象的锁
主线程调用 t1.resume() 后，即使 t1 恢复执行并释放锁，但由于 t2.resume() 可能在 t2 实际挂起前就被调用（时序问题），t2 可能永远无法被唤醒
 */
public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }
        public void run() {
            synchronized (u) {
                System.out.println("进入" + getName());
                Thread.currentThread().suspend();
                System.out.println("退出" + getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}
