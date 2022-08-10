import java.util.concurrent.locks.Lock;

public class Venue {
    Lock l;

	public void dropOff(int load){
		String s = Thread.currentThread().getName();
		int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
		System.out.println("BUS " + id + " is dropping off: LOAD " + load);
		try {
			Thread.sleep((long)(200 + Math.random() * 800));
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
