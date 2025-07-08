package base.syn;

/**
 * @Author
 * @Time 2025/7/7 18:44
 * @Desciption 错误的上锁例子
 * Integer是不可变对象，因此每次自增的时候，它都是新建了一个Integer对象，并把引用赋值给i
 * 所以每次上锁都不是同一个对象，从而导致锁不住
 * 只需要将上锁对象改为instance即可
 * @Version
 */

public class BadLockOnInteger implements Runnable{
    public static Integer i = 0;
    static BadLockOnInteger instance = new BadLockOnInteger();
    @Override
    public void run()
    {
        for (int j = 0; j < 10000000; j++) {
            synchronized (i)
            {
                i++;
            }
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
