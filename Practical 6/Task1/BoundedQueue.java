import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue {
    ReentrantLock enqLock, deqLock;
    Condition notFull, notEmpty;
    AtomicInteger size;
    int capacity;
    int[] queue;
    int head, tail;

    public BoundedQueue(int _capacity) {
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
        notFull = enqLock.newCondition();
        notEmpty = deqLock.newCondition();
        size = new AtomicInteger(0);
        capacity = _capacity;
        queue = new int[capacity];
        head = 0;
        tail = 0;
    }

    public void enq(int x) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        enqLock.lock();
        try {
            while (size.get() == capacity) {
                notFull.await();
            }
            queue[tail] = x;
            tail = (tail + 1) % capacity;
            mustWakeDequeuers = (size.getAndIncrement() == 0);
        } finally {
            System.out.println("Array after Thread " + Thread.currentThread().getName() + " enqueued: " + x);
            for (int i = 0; i < capacity; i++) {
                System.out.print(queue[i] + " ");
            }
            System.out.println();
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

    public int deq() throws InterruptedException {
        int x = 0;
        boolean mustWakeEnqueuers = false;
        deqLock.lock();
        try {
            while (size.get() == 0) {
                notEmpty.await();
            }
            x = queue[head];
            head = (head + 1) % capacity;
            mustWakeEnqueuers = (size.getAndDecrement() == capacity);
        } finally {
            System.out.println("Array after Thread " + Thread.currentThread().getName() + " dequeued head element: " + x);
            for (int i = 0; i < capacity; i++) {
                System.out.print(queue[i] + " ");
            }
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
}
