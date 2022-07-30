import java.util.concurrent.locks.Lock;

class Peterson implements Lock {
    private boolean[] flag = new boolean[2];
    private int victim;

    public void lock() {
        String s = Thread.currentThread().getName();
        int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
        int i = id;
        int j = 1 - id;

        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i) {}
    }

    public void unlock() {
        String s = Thread.currentThread().getName();
        int id = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
        flag[id] = false;
    }
}
