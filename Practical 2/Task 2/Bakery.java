import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Keelan Matthews
// Student Number: 21549967

public class Bakery implements Lock
{
	private AtomicBoolean[] flag;
	private AtomicInteger[] label;
	private int n;

	public Bakery(int nThreads)
	{
		this.n = nThreads;
		flag = new AtomicBoolean[n];
		label = new AtomicInteger[n];
		for (int i = 0; i < n; i++)
		{
			flag[i] = new AtomicBoolean();
			label[i] = new AtomicInteger();
		}
	}

	@Override
	public void lock() {
		String s = Thread.currentThread().getName();
		int i = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
		flag[i].set(true);
		label[i].set(max(label) + 1);
		for (int k = 0; k < n; k++)
		{
			while (k != i && flag[k].get() && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {}
		}
	}

	@Override
	public void unlock() {
		String s = Thread.currentThread().getName();
		int i = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
		flag[i].set(false);
	}

	private int max(AtomicInteger[] arr) {
        int max = Integer.MIN_VALUE;
        for (AtomicInteger e : arr) 
			max = Math.max(max, e.get());

        return max;
    }

	public void lockInterruptibly() throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}

	public boolean tryLock()
	{
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
	{
		throw new UnsupportedOperationException();
	}

	public Condition newCondition()
	{
		throw new UnsupportedOperationException();
	}

}
