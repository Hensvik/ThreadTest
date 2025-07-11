package base.concurrent.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author
 * @Time 2025/7/11 10:52
 * @Desciption CyclicBarrier使用例子
 * @Version
 */

public class CyclicBarrierDemo {
    private static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        public Soldier(String soldierName, CyclicBarrier cyclicBarrier) {
            this.soldier = soldierName;
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                // 等待所有士兵到齐
                cyclicBarrier.await();
                doWork();
                // 等待所有士兵完成工作
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e){
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
                System.out.println(soldier + ":任务完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        public void run() {
            if (flag) {
                System.out.println("司令：【士兵" + N + "个，任务完成】");
            } else {
                System.out.println("司令：【士兵" + N + "个，集合完毕】");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));

        //设置屏障点，主要为了执行该方法

        System.out.println("集合队伍");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "报道");
            allSoldier[i] = new Thread(new Soldier("士兵" + i, cyclicBarrier));
            allSoldier[i].start();

            //下面这段代码会使得第5个士兵线程产生中断
            //如果这么做，我们会得到1个InterruptedException和9个BrokenBarrierException，InterruptedException是中断线程抛出的，其它9个则是等待在当前CyclicBarrier上的线程抛出的
            //这个异常可以避免其他9个线程进行永久的无谓的等待（因为有一个线程已经被中断，所以等待是没有结果的）
            /*if(i == 5){
                allSoldier[i].interrupt();
            }*/
        }
    }
}
