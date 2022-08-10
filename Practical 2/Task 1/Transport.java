public class Transport extends Thread {
    Venue destination;
	private Filter lock;
	private int load = 0;

	public Transport(Venue dest, Filter l){
		destination = dest;
		lock = l;
	}

	@Override
	public void run()
	{
		String s = Thread.currentThread().getName();
		int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
		for (int i = 0; i < 5; i++) {
			load++;
			System.out.println("BUS " + id + " is waiting to drop-off: LOAD " + load);
			lock.lock();
			try {
				destination.dropOff(load);
			} finally {
				System.out.println("BUS " + id + " has left: LOAD " + load);
				lock.unlock();
			}
		}
	}
}
