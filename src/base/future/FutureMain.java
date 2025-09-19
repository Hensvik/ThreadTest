package base.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * 这是一个Future类的设计模式，FutureTask类实现了Future接口，Future接口继承了RunnableFuture接口，
 */

public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //构造FutureTask
        FutureTask<String> futureTask = new FutureTask<String>(new ReadData("a"));
        ExecutorService executor = Executors.newFixedThreadPool(3);
        //执行FutureTask，相当于上例中的client.request("a")发送请求
        //在这里开启线程执行RealData的call()方法
        executor.execute(futureTask);

        System.out.println("请求完毕");
        try {
            //这里依然可以做额外数据操作，这里用sleep替代其它逻辑业务
            Thread.sleep(2000);
            System.out.println("数据：" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("数据：" + futureTask.get());
    }
}
