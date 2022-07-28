public class ThreadClass extends Thread {
    private String name;
    private Scrumboard board;

    public ThreadClass(String name, Scrumboard board) {
        this.name = name;
        this.board = board;
    }

    @Override
    public void run() {
        while (board.getSize() > 0) {
            String task = board.nextItem();
            System.out.println(name + " Task: " + task);
            board.complete(task);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
