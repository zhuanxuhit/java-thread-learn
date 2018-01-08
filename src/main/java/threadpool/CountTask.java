package threadpool;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhuanxu
 */
public class CountTask extends RecursiveTask<Long>{

    private static final int THRESHOLD = 10000;
    private static final int SUB_TASK_NUM = 100;

    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end-start)<THRESHOLD;
        if (canCompute){
            for (long i=start;i<=end;i++){
                sum += i;
            }
        }else {
            // 分成100个小任务
            long step = (start+end)/ SUB_TASK_NUM;
            ArrayList<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i=0;i<SUB_TASK_NUM;i++){
                long lastOne = pos + step;
                if (lastOne>end){
                    lastOne = end;
                }
                CountTask subTask = new CountTask(pos, lastOne);
                pos = pos + step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (CountTask t : subTasks){
                t.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0,200000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long res = result.get();
            System.out.println("sum = " + res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
