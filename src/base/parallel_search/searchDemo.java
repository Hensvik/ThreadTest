package base.parallel_search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * 这是一个并行搜索的demo
 * 现在有一个整型数组arr，我们需要查找其中的元素
 */

public class searchDemo {
    static int[] arr;
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int Thread_Num = 2;
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int searchValue, int beginPos, int endPos){
        int i = 0;
        for(i = beginPos; i < endPos; i++){
            if(result.get() >= 0){
                return result.get();
            }

            if(arr[i] == searchValue){
                //如果设置失败，代表其它线程已经找到了
                if(!result.compareAndSet(-1, i)){
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable<Integer> {
        int begin,end,searchValue;
        public SearchTask(int searchValue, int begin, int end) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }
        public Integer call(){
            return search(begin, end, searchValue);
        }
    }

    public static int pSearch(int searchValue) throws InterruptedException, ExecutionException {
        //subArrSize为线程数，每个线程处理一个子数组
        int subArrSize = arr.length/Thread_Num+1;
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

        //分割搜索任务
        for (int i = 0; i < arr.length; i+=subArrSize) {
            int end = i + subArrSize;
            if(end>arr.length){
                end = arr.length;
            }
            futureList.add(pool.submit(new SearchTask(searchValue, i, end)));
        }
        for (Future<Integer> future : futureList){
            if(future.get()>=0){
                return future.get();
            }
        }
        return -1;
    }
}
