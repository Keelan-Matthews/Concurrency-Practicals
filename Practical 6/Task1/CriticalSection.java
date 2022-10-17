import java.util.concurrent.TimeUnit;

public class CriticalSection {
    BoundedQueue l;

	public CriticalSection() {
		l = new BoundedQueue(10);
	}

	public void enter(int x) {
		try {
			l.enq(x);
			TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 100));
			l.deq();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
