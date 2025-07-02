package base.wait_notify;

/**
 * Created by Hensvik on 2017/4/16.
 * 这是一个简单的wait-notify例子，其中T1线程先wait，T2线程先notify，T1线程被唤醒，然后T1线程结束，T2线程结束。
 */
public class SimpleWN {
    final static Object object = new Object();
    public static class T1 extends Thread {

        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis()+ ":notify begin");
                try {
                    System.out.println(System.currentTimeMillis()+":T1 wait for object");
                    object.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":notify end");
            }
        }
    }

    public static class T2 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis()+":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis()+":T2 end");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }
}
