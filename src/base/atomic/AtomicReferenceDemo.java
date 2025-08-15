package base.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description:
 * 模拟了AtomicReference类在多线程环境下可能存在重复充值的问题
 * 账户被反复充值，原因在于账户余额在多线程下被反复修改使得CAS无法判断当前的数据状态导致，这是典型的ABA问题
 * 具体的解决方法参考AtomicStampedReferenceDemo
 */

public class AtomicReferenceDemo {
    static AtomicReference<Integer> money = new AtomicReference<Integer>(19);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int timestamp = money.get();
            new Thread() {
                public void run(){
                    while (true) {
                        while (true){
                            Integer m = money.get();
                            if (m<20){
                                if(money.compareAndSet(m, m+20)){
                                    System.out.println("余额小于20，充值成功，余额为：" + money.get()+ "元");
                                    break;
                                }
                            }else{
                                //System.out.println("余额大于20，无需充值");
                                break;
                            }
                        }

                    }
                }
            }.start();
        }

        //用户消费线程
        new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    while (true) {
                        Integer m = money.get();
                        if (m > 10) {
                            if (money.compareAndSet(m, m - 10)) {
                                System.out.println("成功消费10元，余额为：" + money.get() + "元");
                                break;
                            }else {
                                System.out.println("余额不足");
                                break;
                            }
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
