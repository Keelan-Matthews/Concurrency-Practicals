import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class Peterson implements Lock {
    private boolean[] flag = new boolean[2];
    private int victim;

    public void lock() {
        String s = Thread.currentThread().getName();
        int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
        int i = id;
        int j = 1 - id;

        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i) {}
    }

    public void unlock() {
        String s = Thread.currentThread().getName();
        int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
        flag[id] = false;
    }

    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }
    public boolean tryLock() {
        throw new UnsupportedOperationException();
    }
    public boolean tryLock(long time, java.util.concurrent.TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
