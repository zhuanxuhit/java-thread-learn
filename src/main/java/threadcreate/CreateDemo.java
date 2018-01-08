package threadcreate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.function.ToIntFunction;

/**
 * @author zhuanxu
 */
public class CreateDemo {
    static class CreateMethod1 extends Thread {
        @Override
        public void run() {
            System.out.println("CreateMethod1\n"+"id: " + Thread.currentThread().getId() + " name: "+currentThread().getName());
        }
    }
    static class CreateMethod2 implements Runnable{

        @Override
        public void run() {
            System.out.println("CreateMethod2\n"+"id: " + Thread.currentThread().getId() + " name: "+Thread.currentThread().getName());
        }
    }

    static class CreateMethod4 implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println("正在进行计算");
            Thread.sleep(1000);
            return 1;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程的方法1:继承Thread类
        Thread thread1 = new CreateMethod1();
        thread1.start();
        // 创建线程的方法2：实现Runnable接口
        Thread thread2 = new Thread(new CreateMethod2());
        thread2.start();
        // 创建线程的方法3：匿名内部类的方式
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("CreateMethod3\n"+"id: " + Thread.currentThread().getId() + " name: "+Thread.currentThread().getName());
            }
        });
        thread3.start();
        // 创建线程的方法4：带返回值的线程
        FutureTask<Integer> task = new FutureTask<>(new CreateMethod4());
        System.out.println("主线程不阻塞");
        Thread thread4 = new Thread(task);
        thread4.start();
        Integer result = task.get();
        System.out.println("线程执行结果为:"+result);
        // 创建线程的方法4：定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定期执行任务 " + "id: " + Thread.currentThread().getId() + " name: "+Thread.currentThread().getName());
            }
        },0,100);
        // 创建线程的方法5：线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i=0;i<100;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("CreateMethod5\n"+"id: " + Thread.currentThread().getId() + " name: "+Thread.currentThread().getName());
                }
            });
        }
        threadPool.shutdown();
        // 创建线程的方法7：使用Spring来实现线程
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        CreateService service =  context.getBean(CreateService.class);
        service.a();
        service.b();
        context.close();

        // 创建线程的方法7：lambda表达式
        System.out.println(add(Arrays.asList(10,20,30,40)));
        // 最后关闭定时器
        timer.cancel();
    }

    public static int add(List<Integer> values){
        return values.parallelStream().mapToInt(i -> i * 2).sum();
    }
}
