public class VotingStation {
    MCSQueue l;

	public VotingStation() {
		l = new MCSQueue();
	}

	public void castBallot(int i){
		try {
			l.setPerson(i);
            l.lock();
			Thread.sleep((int)(Math.random() * 800 + 200));
			System.out.println(Thread.currentThread().getName() + " sees person " + i + " has cast a vote");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            l.unlock();
			System.out.println(" ");
			System.out.println(l.getQueue());
			System.out.println(" ");
        }
	}
}
