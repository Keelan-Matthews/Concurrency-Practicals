public class VotingStation {
    Timeout l;

	public VotingStation() {
		l = new Timeout();
	}

	public void castBallot(int i){
		try {
            l.lock();
			Thread.sleep((int)(Math.random() * 800 + 200));
			System.out.println(Thread.currentThread().getName() + " sees person " + i + " has cast a vote");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
			System.out.println(" ");
            l.unlock();
			System.out.println(" ");
        }
	}
}
