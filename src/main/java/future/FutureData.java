package future;

/**
 * @author zhuanxu
 */
public class FutureData implements Data{
    private RealData realData = null;
    private boolean isReady = false;

    public synchronized void setRealData(RealData data) {
        if (isReady){
            return;
        }
        this.realData = data;
        isReady = true;
        notifyAll();
    }

    @Override
    public synchronized String getResult() {
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return realData.getResult();
    }
}
