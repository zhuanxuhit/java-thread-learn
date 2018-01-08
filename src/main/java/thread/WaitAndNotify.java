package thread;

/**
 * @author zhuanxu
 */
public class WaitAndNotify {
    final static Object OBJECT = new Object();

    public static class Wait implements Runnable{

        @Override
        public void run() {
            synchronized (OBJECT){
                System.out.println(System.currentTimeMillis() + ": Wait start!");
                System.out.println(System.currentTimeMillis() + " Wait wait for object");
                try {
                    OBJECT.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(System.currentTimeMillis() + ": Wait end!");
            }
        }
    }

    public static class Notify implements Runnable {

        @Override
        public void run() {
            synchronized (OBJECT){
                System.out.println(System.currentTimeMillis() + ": Notify start! notify one thread.");
                OBJECT.notify();
                System.out.println(System.currentTimeMillis() + ": Notify end!");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Wait());
        Thread t2 = new Thread(new Notify());
        t1.start();
        t2.start();
    }
}
