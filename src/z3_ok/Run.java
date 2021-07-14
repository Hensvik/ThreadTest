package z3_ok;

public class Run {
    public static void main(String[] args) {
        MyService myService = new MyService();
        MyThreadA a1 = new MyThreadA(myService);
        a1.start();
        MyThreadA a2 = new MyThreadA(myService);
        a2.start();
        MyThreadA a3 = new MyThreadA(myService);
        a3.start();
    }
}
