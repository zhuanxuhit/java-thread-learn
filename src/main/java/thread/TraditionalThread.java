package thread;

/**
 * @author zhuanxu
 */
public class TraditionalThread {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("1:" + Thread.currentThread().getName());
                    System.out.println("2:" + this.getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("1:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"thread2");
        thread2.start();
    }

}
