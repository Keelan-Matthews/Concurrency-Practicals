public class Marshal extends Thread {

    private VotingStation vs;

	Marshal(VotingStation _vs)
	{
		vs = _vs ;
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + " told person " + i + " to enter the station");
			vs.castBallot(i);
		}
	}
}
