package base.parallel_pipeline;

/**
 * @description:
 * 这是一个通过多线程流水线的实现方式实现的线程编排
 */

public class ParallelPipelineDemo {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                Msg msg = new Msg();
                msg.i = i;
                msg.j = j;
                msg.orgStr = "((" + i + " + " + j + ")* " + i + ")/2";
                Plus.bq.add(msg);
            }
        }
    }
}
