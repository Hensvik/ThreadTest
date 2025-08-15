package base.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * SimpleDateFormat对象在多线程下为非线程安全对象，所以一般会使用ThreadLocal对象来保证多线程安全。
 *
 * 如果在JDK7中，ThreadLocal对象会自动清理无效对象，但是JDK8中，ThreadLocal对象不会自动清理无效对象。
 */

public class ThreadLocalDemo_Gc {
    static volatile ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>(){
        protected void finalize() throws Throwable{
            System.out.println(this.toString() + " is gc");
        }
    };

    static volatile CountDownLatch cd = new CountDownLatch(10000);

    public static class ParseDate implements Runnable{

        int i = 0;
        public ParseDate(int i){
            this.i = i;
        }

        @Override
        public void run() {
            try{
                if(tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + ":create SimpleDateFormat");
                }
                Date t = tl.get().parse("2015-03-29 23:59:59");
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //初始化大小为10的线程池，并执行对应代码
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("mission complete!");
        tl = null;
        System.gc();
        System.out.println("first main gc complete!!");
        //在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象
        tl = new ThreadLocal<>();
        cd = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second main gc complete!!");
    }
}
