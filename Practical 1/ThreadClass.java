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
            String s = currentThread().getName();
            int threadId = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
            lock.lock(threadId);

            try {
                String task = board.nextItem();
                System.out.println(currentThread().getName() + " Task: " + task);
                board.complete(task);
            } finally {
                lock.unlock(threadId);
            }
        }
    }
}
