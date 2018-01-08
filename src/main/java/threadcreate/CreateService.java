package threadcreate;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhuanxu
 */
@Service
public class CreateService {
    @Async
    public void a() {
        System.out.println("CreateMethod7 a\n"+"id: " + Thread.currentThread().getId() + " name: "+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Async
    public void b() {
        System.out.println("CreateMethod7 b\n"+"id: " + Thread.currentThread().getId() + " name: "+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
