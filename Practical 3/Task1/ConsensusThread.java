public class ConsensusThread extends Thread {
	private Consensus<Integer> consensus;

	ConsensusThread(Consensus<Integer> consensusObject) {
		consensus = consensusObject;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			int value = (int) (Math.random() * 100) + 100;
			consensus.propose(value);	

			try {
				Thread.sleep((int) (Math.random() * 50) + 50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			consensus.decide();
	
			try {
				Thread.sleep((int) (Math.random() * 50) + 50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
