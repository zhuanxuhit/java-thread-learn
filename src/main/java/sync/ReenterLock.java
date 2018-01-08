package sync;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable{
    static ReentrantLock lock  = new ReentrantLock();
    static int i = 0;

    @Override
    public void run() {
        for (int j=0;j<1000000;j++){
            lock.lock();
            i++;
            lock.unlock();
//            lock.lockInterruptibly();
//            lock.tryLock(1)
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ReenterLock());
        Thread t2 = new Thread(new ReenterLock());
        t1.start();
        t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
