public class RMWConsensus extends ConsensusProtocol<Integer> {
    public RMWConsensus(int threadCount) {
        super(threadCount);
    }

    public void decide() {
        int i = Integer.parseInt(Thread.currentThread().getName());
        System.out.println("Register value on decide call for Thread-" + Thread.currentThread().getName() + ": " + proposed[i]);
        int j = (i + 1) % proposed.length;
        if (this.proposed[i] != this.proposed[j]) { //Unequal, hasn't been overwritten yet
            this.proposed[j] = this.proposed[i]; //My value taks priority
            System.out.println("Thread-" + Thread.currentThread().getName() + " is first and overwrites Thread-" + j);
        }

        System.out.println("Decided value for Thread-" + Thread.currentThread().getName() + ": " + this.proposed[i]);
        System.out.println("");
    }
}
