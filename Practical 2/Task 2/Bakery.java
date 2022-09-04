import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Keelan Matthews
// Student Number: 21549967

public class Bakery implements Lock
{
	private boolean[] flag;
	private int[] label;
	private int n;

	public Bakery(int nThreads)
	{
		this.n = nThreads;
		flag = new boolean[n];
		label = new int[n];
		for (int i = 0; i < n; i++)
		{
			flag[i] = false;
			label[i] = 0;
		}
	}

	@Override
	public void lock() {
		String s = Thread.currentThread().getName();
		flag[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))] = true;
		label[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))] = max(label) + 1;
		for (int k = 0; k < n; k++)
		{
			while (k != Integer.parseInt(s.substring(s.lastIndexOf("-") + 1)) && 
					flag[k] && ((label[k] < label[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))]) || 
					((label[k] == label[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))]) && 
					k < Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))))) {}
		}
	}

	@Override
	public void unlock() 
	{
		String s = Thread.currentThread().getName();
		flag[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))] = false;
	}

	private int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int e : arr) 
			max = Math.max(max, e);

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
