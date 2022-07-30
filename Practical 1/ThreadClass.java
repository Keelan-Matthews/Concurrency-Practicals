public class ThreadClass extends Thread {
    private Scrumboard board;
    private Peterson lock;

    public ThreadClass(Scrumboard board, Peterson l) {
        this.board = board;
        lock = l;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();

            try {
                String task = board.nextItem();
                System.out.println(currentThread().getName() + " Task: " + task);
                board.complete(task);
            } finally {
                lock.unlock();
            }
        }
    }
}
