import java.util.concurrent.TimeUnit;

public class VotingStation {
    Timeout l;

	public VotingStation() {
		l = new Timeout();
	}

	public void castBallot(int i){
		try {
			if (l.tryLock(1000, TimeUnit.MILLISECONDS)) {
				System.out.println("Marshal-" + Thread.currentThread().getName() + " sees person " + i + " has cast a vote");
				Thread.sleep((int)(Math.random() * 800 + 200));
				l.unlock();
			} else {
				System.out.println("Marshal-" + Thread.currentThread().getName() + " timed out while trying to cast ballot for person " + i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
