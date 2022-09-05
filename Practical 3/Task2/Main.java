public class Main {
    public static void main(String[] args) {
        int numThreads = 10;
        int threadCount[] = new int[numThreads];
        TASLock tasLock = new TASLock();
        BackoffLock backoffLock = new BackoffLock();
        TTASLock ttasLock = new TTASLock();

        int TASLock[] = new int[numThreads];
        int TTASLock[] = new int[numThreads];
        int BackoffLock[] = new int[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threadCount[i] = (int) (i*2.5)+5;
            MainThread[] threads = new MainThread[threadCount[i]];

            for (int j = 0; j < threads.length; j++) {
                threads[j] = new MainThread(tasLock, ttasLock, backoffLock);
            }
    
            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
            }

            for (int j = 0; j < threads.length; j++) {
                try {
                    threads[j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < threads.length; j++) {
                TASLock[i] += threads[j].getTime(0);
                TTASLock[i] += threads[j].getTime(1);
                BackoffLock[i] += threads[j].getTime(2);
            }
        }

        System.out.println("Number of threads: " + printArray(threadCount));
        System.out.println("-------------------------------------------------------------");
        System.out.println("TASLock: " + printArray(TASLock) + " time in ms");
        System.out.println("TTASLock: " + printArray(TTASLock) + " time in ms");
        System.out.println("BackoffLock: " + printArray(BackoffLock) + " time in ms");
    }

    public static String printArray(int[] array) {
        String output = "";
        output += "[";
        for (int i = 0; i < array.length; i++) {
            output += array[i];
            if (i < array.length - 1) {
                output += ", ";
            }
        }
        output += "]";

        return output;
    }
}