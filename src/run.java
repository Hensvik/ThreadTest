public class run {
    public static void main(String[] args) {
        test mythread = new test();
        System.out.println("begin ==" + mythread.isAlive());
        mythread.start();
        System.out.println("end ==" + mythread.isAlive());
    }
}