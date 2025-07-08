package base.array;

import java.util.ArrayList;

/**
 * @Author
 * @Time 2025/7/7 18:09
 * @Desciption ArrayList多线程下的错误例子
 * @Version
 */

public class ArrayListMultiThread{
    static ArrayList<Integer> list = new ArrayList<Integer>();
    public static class AddThread implements Runnable{
        public void run()
        {
            for(int i=0;i<1000000;i++)
            {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("size:"+list.size());
    }
}
