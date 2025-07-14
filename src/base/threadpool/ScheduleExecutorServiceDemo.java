package base.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务为每两秒执行并打印当前时间戳
 */

public class ScheduleExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        //如果前面的任务没有完成，则调度不会启动
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis()/1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },0,2, TimeUnit.SECONDS);
    }
}
