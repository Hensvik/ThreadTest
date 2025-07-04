package base.daemon;

/**
 * @Author
 * @Time 2025/7/4 11:26
 * @Desciption
 * 这个例子不会永远死循环执行下去，是因为主线程在启动守护线程 DaemonT 后调用了 Thread.sleep(2000)，
 * 这会让主线程休眠 2 秒后自然结束。由于 Java 的进程会在所有非守护线程结束后自动退出，
 * 而 DaemonT 线程已经被设置为守护线程（通过 t.setDaemon(true)），因此即使它仍在运行，整个程序也会随着主线程的结束而终止。
 * @Version
 */

public class DaemonDemo {
    public static class DaemonT extends Thread {
        public void run() {
            while (true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t = new DaemonT();
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }
}
