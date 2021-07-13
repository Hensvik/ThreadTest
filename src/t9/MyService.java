package t9;

public class MyService {
    public MyOneList addServiceMethod(MyOneList list, String data) {
        try {
            if (list.getSize() < 1) {
                Thread.sleep(2000);//模拟从远程端花费2s取回数据
                list.add(data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
