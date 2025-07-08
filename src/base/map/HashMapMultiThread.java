package base.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Time 2025/7/7 18:09
 * @Desciption 多线程下的HashMap出现的问题
 * 运行时可能出现三种情况
 * 1. 运行结果为200000，符合预期
 * 2. 运行程序不符合预期，而是一个小于100000的数字
 * 3. 程序永远无法结束
 * @Version
 */

public class HashMapMultiThread {
    static Map<String,String> map = new HashMap<String,String>();

    public static class AddThread implements Runnable{
        int start = 0;
        public AddThread(int start){
            this.start = start;
        }

        @Override
        public void run() {
            for(int i = start; i < 100000; i++){
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("size:"+map.size());
    }
}
