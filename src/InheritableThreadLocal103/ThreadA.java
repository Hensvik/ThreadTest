package InheritableThreadLocal103;

public class ThreadA extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Userinfo userinfo = Tools.tl.get();
                System.out.println("在ThreadA线程中取值=" + userinfo.getUsername() + " " + userinfo.hashCode());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}