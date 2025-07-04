package base.syn;

/**
 * @Author
 * @Time 2025/7/4 11:43
 * @Desciption
 * synchronized关键字的基础用法，和Sync的效果一样，差别在于synchronized上锁的对象是increase方法
 * @Version
 */

public class AccountingSync2 implements Runnable{
    static AccountingSync2 instance = new AccountingSync2();
    static int i = 0;
    public synchronized void increase()
    {
        i++;
    }
    public void run()
    {
        for(int j = 0; j < 1000000; j++)
        {
            increase();
        }
    }
    public static void main(String[] args) throws InterruptedException
    {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
