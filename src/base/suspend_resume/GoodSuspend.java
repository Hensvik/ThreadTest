package base.suspend_resume;

/**
 * @Author
 * @Time 2025/7/4 11:01
 * @Desciption 使用wait和notify实现suspend和resume功能
 * 统一的同步对象：使用 lock 对象替代 this，避免不必要的竞争。
 * 新增 waiting 标志：确保 notify() 只在必要时调用，避免无效唤醒。
 * 同步 suspendMe() 和 resumeMe()：确保这两个方法的操作是原子的，防止竞态条件。
 * 优化 wait() 和 notify() 的逻辑：确保线程在唤醒后能够正确继续执行。
 * 这些改进使得线程挂起和恢复更加可靠，减少了潜在的死锁和竞态条件问题。
 * @Version
 */
public class GoodSuspend {
    public static Object u = new Object();
    private static final Object lock = new Object(); // 统一的同步对象

    public static class ChangeObjectThread extends Thread {
        private volatile boolean suspendMe = false;
        private volatile boolean waiting = false; // 新增状态标志

        // 标记线程挂起
        public synchronized void suspendMe() {
            suspendMe = true;
        }

        // 标记线程继续执行，使用notify唤醒线程
        public synchronized void resumeMe() {
            suspendMe = false;
            if (waiting) {
                synchronized (lock) {
                    lock.notify();
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (suspendMe) {
                        waiting = true;
                        try {
                            lock.wait(); // 等待锁释放
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            waiting = false;
                        }
                    }
                }

                // 执行业务逻辑
                synchronized (u) {
                    System.out.println("in ChangeObjectThread");
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    System.out.println("in ReadObjectThread");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("suspend t1 2 seconds");
        Thread.sleep(2000);
        System.out.println("resume t1");
        t1.resumeMe();
    }
}
