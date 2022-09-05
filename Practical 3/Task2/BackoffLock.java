import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private int MIN_DELAY = 100;
    private int MAX_DELAY = 1000;

    @Override
    public void lock() {
        int delay = MIN_DELAY;
        while (true) {
            while (state.get()) {}
            if (!state.getAndSet(true)) {
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (delay < MAX_DELAY) {
                delay *= 2;
            }
        }
        
    }

    @Override
    public void unlock() {
        state.set(false);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
    
}
