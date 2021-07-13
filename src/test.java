import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class test extends Thread{
    @Override
    public void run() {
        System.out.println("run=" + this.isAlive());
    }

}

