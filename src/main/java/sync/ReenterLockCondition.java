package sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReenterLockCondition implements Runnable{
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    @Override
    public void run() {
//        ReentrantReadWriteLock
//        CountDownLatch
//        CyclicBarrier
    }
}
