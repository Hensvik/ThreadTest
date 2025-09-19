package base.future;

import java.util.concurrent.Callable;

public class ReadData implements Callable<String> {
    private String para;
    public ReadData(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){

            }
        }
        return sb.toString();
    }
}
