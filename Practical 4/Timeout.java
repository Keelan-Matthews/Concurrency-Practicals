import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Timeout implements Lock {
    private MCSQueue q;
    private int timeout;

    public Timeout(int _timeout){
        q = new MCSQueue();
        timeout = _timeout;
    }

    public void lock(){
        q.lock();
    }

    public void unlock(){
        q.unlock();
    }

    public void lockInterruptibly() throws InterruptedException{
        throw new UnsupportedOperationException();
    }

    public boolean tryLock(){
        throw new UnsupportedOperationException();
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException{
        throw new UnsupportedOperationException();
    }

    public Condition newCondition(){
        throw new UnsupportedOperationException();
    }
}
