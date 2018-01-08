package thread;

import oracle.jrockit.jfr.jdkevents.ThrowableTracer;

/**
 * @author zhuanxu
 */
public class InterruptedThread {
    public static class Interrupted implements Runnable{
        @Override
        public void run() {
            while (true){
                if (Thread.interrupted()){
                    System.out.println("Interrupted!");
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("interrupted when sleep");
                    Thread.currentThread().interrupt();
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Interrupted());
        t1.start();
        Thread.sleep(100);
        t1.interrupt();
        Thread.sleep(200);
    }
}
