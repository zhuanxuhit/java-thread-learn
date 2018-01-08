package thread;

/**
 * @author zhuanxu
 */
public class GoodSuspend {
    final static Object OBJECT = new Object();

    static class ChangeObjectThread extends Thread {
        protected volatile boolean suspendMe = false;

        public void suspendMe() {
            suspendMe = true;
        }

        public void resumeMe() {
            suspendMe = false;
            synchronized (this) {
                notify();
            }
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (suspendMe) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                synchronized (OBJECT) {
                    System.out.println("in ChangeObjectThread");
                }
                Thread.yield();
            }
        }
    }

    static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (OBJECT) {
                    System.out.println("in ReadObjectThread");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("suspend t1 1 s");
        Thread.sleep(2000);
        System.out.println("resume t1");
        t1.resumeMe();
    }
}
