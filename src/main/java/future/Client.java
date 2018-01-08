package future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author zhuanxu
 */
public class Client {
    public Data request(final String queryStr){
        FutureData futureData = new FutureData();

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-set-data-%d").build();
//        Executors.newSingleThreadExecutor()
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1,1,
                0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()->{
            RealData realData = new RealData(queryStr);
            futureData.setRealData(realData);
        });

        return futureData;
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        Thread.sleep(2000);
        System.out.println("真实数据是：" + data.getResult());
    }
}
