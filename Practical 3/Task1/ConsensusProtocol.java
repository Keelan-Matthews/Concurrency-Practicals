public abstract class ConsensusProtocol<T> implements Consensus<T>
{
	public volatile Object[] proposed;

	public ConsensusProtocol(int threadCount)	{
		proposed = new Object[threadCount];
	}

	public void propose(T value){
		String s = Thread.currentThread().getName();
		proposed[Integer.parseInt(s.substring(s.lastIndexOf("-") + 1))] = value;
		System.out.println("Proposed value for Thread-" + Thread.currentThread().getName() + ": " + value);
	}

	abstract public void decide();
}
