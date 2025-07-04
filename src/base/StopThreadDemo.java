package base;

/**
 * @Author Hensvik
 * @Time 2025/7/4 10:32
 * @Desciption 提供一个在高并发环境下可以安全推出线程的例子,注解内的内容可以放出来
 * @Version
 */

public class StopThreadDemo extends Thread{
    public User u = new User();
    volatile boolean stopMe = false;

    public void stopMe(){
        stopMe = true;
    }

    @Override
    public void run(){
        while (true){
            if(stopMe){
                System.out.println("exit by stop me");
                break;
            }

            synchronized (u){
                int v = (int)(System.currentTimeMillis() / 1000);
                u.setId(v);
                //do sth else
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                u.setName("name" + v);
            }
            Thread.yield();
        }
    }

    public class User{
        private int id;
        private String name;
        public void setId(int id){
            this.id = id;
        }
        public void setName(String name){
            this.name = name;
        }
    }
}
