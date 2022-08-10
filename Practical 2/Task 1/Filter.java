import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Keelan Matthews
// Student Number: 21549967

public class Filter implements Lock {
	private AtomicInteger[] level;
	private AtomicInteger[] victim;
	private int n;

	public Filter(int nThreads) {
		this.n = nThreads;
		level = new AtomicInteger[n];
		victim = new AtomicInteger[n];
		for (int i = 0; i < n; i++) {
			level[i] = new AtomicInteger();
			victim[i] = new AtomicInteger();
		}
	}

	@Override
	public void lock() {
		String s = Thread.currentThread().getName();
		int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));

		for (int i = 1; i < n; i++) {
			level[id].set(i);
			victim[i].set(id);

			for (int k = 0; k < n; k++) {
				while ((k != id) && (level[k].get() >= i && victim[i].get() == id)) {}
			}
		}
	}

	@Override
	public void unlock() {
		String s = Thread.currentThread().getName();
		int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
		level[id].set(0);
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
