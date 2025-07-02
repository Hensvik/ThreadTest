package wait_notify_size5;

/**
 * Created by Hensivk on 2018/4/26.
 * 这是一个正确使用wait和notify的例子
 */
public class Run {
    public static void main(String[] args) {

        try {
            Object lock = new Object();

            ThreadA a = new ThreadA(lock);
            a.start();

            Thread.sleep(50);

            ThreadB b = new ThreadB(lock);
            b.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
