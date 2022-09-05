public class MainThread extends Thread {
    private TASLock tasLock;
    private TTASLock ttasLock;
    private BackoffLock backoffLock;
    private int[] time;

    public MainThread(TASLock tasLock, TTASLock ttasLock, BackoffLock backoffLock) {
        this.tasLock = tasLock;
        this.ttasLock = ttasLock;
        this.backoffLock = backoffLock;
        this.time = new int[3];
    }

    public int getTime(int index) {
        return time[index];
    }
    
    public void run() {
        // Lock and unlock tasLock
        int startingTime = (int) System.currentTimeMillis();
        try {
            tasLock.lock();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            tasLock.unlock();
        }
        time[0] += (int) System.currentTimeMillis() - startingTime;

        // Lock and unlock ttasLock
        startingTime = (int) System.currentTimeMillis();
        try {
            ttasLock.lock();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ttasLock.unlock();
        }
        time[1] += (int) System.currentTimeMillis() - startingTime;

        // Lock and unlock backoffLock
        startingTime = (int) System.currentTimeMillis();
        try {
            backoffLock.lock();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            backoffLock.unlock();
        }
        time[2] += (int) System.currentTimeMillis() - startingTime;

    }
}
