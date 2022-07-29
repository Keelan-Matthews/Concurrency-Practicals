class Peterson implements Lock {
    private boolean[] flag = new boolean[2];
    private int victim;

    public void lock(int id) {
        int i = id;
        int j = 1 - id;
        
        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i) {};
    }

    public void unlock(int id) {
       flag[id] = false;  
    }
    
}
