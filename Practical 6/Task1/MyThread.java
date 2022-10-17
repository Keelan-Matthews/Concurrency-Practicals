public class MyThread extends Thread {

    private CriticalSection cs;

	MyThread(CriticalSection _cs)
	{
		cs = _cs;
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			cs.enter(i);
		}
	}
}
