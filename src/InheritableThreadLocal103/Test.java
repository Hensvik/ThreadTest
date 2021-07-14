package InheritableThreadLocal103;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Userinfo userinfo = new Userinfo();
        System.out.println("A userinfo " + userinfo.hashCode());
        userinfo.setUsername("中国");
        if (Tools.tl.get() == null) {
            Tools.tl.set(userinfo);
        }
        System.out.println("       在Main线程中取值=" + Tools.tl.get().getUsername() + " " + Tools.tl.get().hashCode());
        Thread.sleep(100);
        ThreadA a = new ThreadA();
        a.start();
        Thread.sleep(5000);
        Tools.tl.get().setUsername("美国");
    }
}
