package base.syn;

/**
 * @Author
 * @Time 2025/7/4 11:43
 * @Desciption
 * synchronized关键字的基础用法，并发操作时对AccountingSync类上锁
 * @Version
 */

public class AccountingSync implements Runnable{
    static AccountingSync instance = new AccountingSync();
    static int i = 0;

    @Override
    public void run() {
        synchronized (AccountingSync.class) {
            for (int j = 0; j < 1000; j++) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
