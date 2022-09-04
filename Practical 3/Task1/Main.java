public class Main {
    public static void main(String[] args) {
        int threadCount = 2;
        
        ConsensusProtocol<Integer> consensus = new RMWConsensus(threadCount);
        
        ConsensusThread[] threads = new ConsensusThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new ConsensusThread(consensus);
            threads[i].setName(Integer.toString(i));
            threads[i].start();
        }
    }
}
