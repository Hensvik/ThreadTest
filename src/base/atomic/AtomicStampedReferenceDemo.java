package base.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description:
 * 使用AtomicStamptedReference解决线程并发问题，充值和消费线程并发执行，解决方法：使用AtomicStampedReference
 */

public class AtomicStampedReferenceDemo {
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19,0);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int timestamp = money.getStamp();
            new Thread() {
                public void run(){
                    while (true) {
                        while (true){
                            Integer m = money.getReference();
                            if (m<20){
                                if(money.compareAndSet(m, m+20,timestamp,timestamp+1)){
                                    System.out.println("余额小于20，充值成功，余额为：" + money.getReference()+ "元");
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
                        int timestamp = money.getStamp();
                        Integer m = money.getReference();
                        if (m > 10) {
                            if (money.compareAndSet(m, m - 10,timestamp,timestamp+1)) {
                                System.out.println("成功消费10元，余额为：" + money.getReference() + "元");
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
