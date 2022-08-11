import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Keelan Matthews
// Student Number: 21549967

public class Filter implements Lock {
	private int[] level;
	private int[] victim;
	private int n;

	public Filter(int nThreads) {
		this.n = nThreads;
		level = new int[n];
		victim = new int[n];
		for (int i = 0; i < n; i++) {
			level[i] = 0;
		}
	}

	@Override
	public void lock() {
		String s = Thread.currentThread().getName();

		for (int i = 1; i < n; i++) {
			level[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))] = i;
			victim[i] = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));

			for (int k = 0; k < n; k++) {
				while (k != Integer.parseInt(s.substring(s.lastIndexOf("-") + 1)) && level[k] >= i && victim[i] == Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))) {}
			}
		}
	}

	@Override
	public void unlock() {
		String s = Thread.currentThread().getName();
		level[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))] = 0;
	}

	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}

}
