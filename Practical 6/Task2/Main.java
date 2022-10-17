public class Main {
    public static void main(String args[]){
        CriticalSection cs = new CriticalSection();
        int numThreads = 3;
        MyThread m[] = new MyThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            m[i] = new MyThread(cs);
            m[i].setName(Integer.toString(i));
            m[i].start();
        }
    }
}
