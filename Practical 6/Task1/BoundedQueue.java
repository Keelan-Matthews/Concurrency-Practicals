import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue {
    ReentrantLock enqLock, deqLock;
    Condition notFull, notEmpty;
    AtomicInteger size;
    int capacity;
    String[] queue;
    int head, tail;

    public BoundedQueue(int _capacity) {
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
        notFull = enqLock.newCondition();
        notEmpty = deqLock.newCondition();
        size = new AtomicInteger(0);
        capacity = _capacity;
        queue = new String[capacity];
        head = 0;
        tail = 0;

        for (int i = 0; i < capacity; i++) {
            queue[i] = "_";
        }
    }

    public void enq(int x) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        enqLock.lock();
        try {
            while (size.get() == capacity) {
                notFull.await();
            }
            queue[tail] = Integer.toString(x);
            printQueue("enqueue", Thread.currentThread().getName(), Integer.toString(x));
            tail = (tail + 1) % capacity;
            mustWakeDequeuers = (size.getAndIncrement() == 0);
        } finally {
            enqLock.unlock();
        }
        if (mustWakeDequeuers) {
            deqLock.lock();
            try {
                notEmpty.signalAll();
            } finally {
                deqLock.unlock();
            }
        }
    }

    public String deq() throws InterruptedException {
        String x = "";
        boolean mustWakeEnqueuers = false;
        deqLock.lock();
        try {
            while (size.get() == 0) {
                notEmpty.await();
            }
            x = queue[head];
            queue[head] = "_";
            printQueue("dequeue", Thread.currentThread().getName(), x);
            head = (head + 1) % capacity;
            mustWakeEnqueuers = (size.getAndDecrement() == capacity);
        } finally {
            deqLock.unlock();
        }
        if (mustWakeEnqueuers) {
            enqLock.lock();
            try {
                notFull.signalAll();
            } finally {
                enqLock.unlock();
            }
        }
        return x;
    }

    public void printQueue(String type, String thread, String x) {
        enqLock.lock();
        deqLock.lock();
        try {
            System.out.println("\n" + type.toUpperCase() + "\nArray after Thread " + thread + " " + type + "d: " + x);
            for (int i = 0; i < capacity; i++) {
                System.out.print(queue[i] + " ");
            }
            System.out.println("");
        } finally {
            enqLock.unlock();
            deqLock.unlock();
        }
    }
}
