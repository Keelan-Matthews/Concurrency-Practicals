public class Main {
    public static void main(String args[]){
        CriticalSection cs = new CriticalSection();
        MyThread m[] = new MyThread[5];

        for (int i = 0; i < 5; i++) {
            m[i] = new MyThread(cs);
            m[i].setName(Integer.toString(i));
            m[i].start();
        }
    }
}
