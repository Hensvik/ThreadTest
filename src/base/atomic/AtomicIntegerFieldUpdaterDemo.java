package base.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description:
 * 现在思考一个场景。假设某地进行了一场选举，如果选民投了候选人一票，就记为1，否则为0，最终选票即为数据的求和
 * 上述代码模拟了一个计票场景，候选人的选票得票记录放在Candidate.score中，注意它是一个普通的volatile变量，而volatile是非线程安全的
 * AtomicIntegerFieldUpdater用来对Candidate.score进行写入。后续的allScore用来检查AtomicIntegerFieldUpdater的正确性。
 * 如果AtomicIntegerFieldUpdater正确，则AtomicIntegerFieldUpdater.get()的值应该等于AtomicInteger.get()的值。
 * 但是有几个注意事项：
 * 1.Updater只能修改它可见范围内的变量。因为Updater使用反射得到这个变量。如果变量不可见就会出错。比如把score定义成private
 * 2.为了确保变量被正确读取，它必须是volatile的。
 * 3.由于CAS操作会通过对象实例中的偏移量直接进行赋值，因此不支持static字段
 */

public class AtomicIntegerFieldUpdaterDemo {
    public static class Candidate {
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    //检查Update是否正确
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) {
        Candidate c = new Candidate();
        Thread[] t = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            t[i] = new Thread(){
                public void run(){
                    if (Math.random() > 0.4) {
                        scoreUpdater.incrementAndGet(c);
                        allScore.incrementAndGet();
                    }
                }
            };
            t[i].start();
        }

        for(int i = 0; i < 10000; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("score:"+ c.score);
            System.out.println("allScore:"+ allScore.get());
        }
    }
}
