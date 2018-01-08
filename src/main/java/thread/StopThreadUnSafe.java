package thread;

/**
 * @author zhuanxu
 */
public class StopThreadUnSafe {
    final static User U = new User();
    public static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        @Override
        public String toString() {
            return "User [id=" + id + ",name=" + name + "]";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ChangeUserUnsafeThread extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (U){
                    int id = (int) (System.currentTimeMillis()/1000);
                    U.setId(id);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    U.setName(String.valueOf(id));
                }
                Thread.yield();
            }
        }
    }
    public static class ReadUserThread extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (U){
                    if (U.getId() != Integer.parseInt(U.getName())){
                        System.out.println(U.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static class ChangeUserSafeThread extends Thread {
        private boolean stopMe = false;

        public void stopMe() {
            this.stopMe = true;
        }

        @Override
        public void run() {
            while (true){
                if (stopMe){
                    System.out.println("exit by stop me");
                    break;
                }
                synchronized (U){
                    int id = (int) (System.currentTimeMillis()/1000);
                    U.setId(id);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    U.setName(String.valueOf(id));
                }
                Thread.yield();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new ReadUserThread().start();
        while (true){
//            Thread thread = new ChangeUserUnsafeThread();
            ChangeUserSafeThread t = new ChangeUserSafeThread();
            t.start();
            Thread.sleep(150);
            t.stopMe();
        }
    }
}
