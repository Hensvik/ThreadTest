package base.syn;

public class AccountingSyncBad implements Runnable{
    public static int i = 0;

    public synchronized void increase()
    {
        i++;
    }

    @Override
    public void run()
    {
        for(int j = 0; j < 10000000; j++)
        {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        //由于这里是操作的不是同一个对象，所以会出现数据不是20000000的情况
        AccountingSyncBad t1 = new AccountingSyncBad();
        AccountingSyncBad t2 = new AccountingSyncBad();
        Thread t3 = new Thread(t1);
        Thread t4 = new Thread(t2);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        System.out.println(i);
    }
}
