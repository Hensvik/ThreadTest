package base.threadpool;

public class DivTask implements Runnable{
    int a,b;
    public DivTask(int a, int b)
    {
        this.a = a;
        this.b = b;
    }

    public void run()
    {
        System.out.println(a/b);
    }
}
