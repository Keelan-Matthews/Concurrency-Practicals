import java.util.concurrent.atomic.AtomicInteger;

public class BoundedQueue {
    AtomicInteger size;
    int capacity;
    String[] queue;
    int head, tail;

    public BoundedQueue(int _capacity) {
        size = new AtomicInteger(0);
        capacity = _capacity;
        queue = new String[capacity];
        head = 0;
        tail = 0;

        for (int i = 0; i < capacity; i++) {
            queue[i] = "_";
        }
    }

    public void enq(int x) throws IndexOutOfBoundsException {
        while (true) {
            int s = size.get();
            if (s == capacity) {
                throw new IndexOutOfBoundsException("Queue is full");
            } else {
                if (size.compareAndSet(s, s + 1)) {
                    queue[tail] = Integer.toString(x);
                    printQueue("enqueue", Thread.currentThread().getName(), Integer.toString(x));
                    tail = (tail + 1) % capacity;
                    break;
                }
            }
        }
    }

    public String deq() throws IndexOutOfBoundsException {
        while (true) {
            int s = size.get();
            if (s == 0) {
                throw new IndexOutOfBoundsException("Queue is empty");
            } else {
                if (size.compareAndSet(s, s - 1)) {
                    String x = queue[head];
                    queue[head] = "_";
                    printQueue("dequeue", Thread.currentThread().getName(), x);
                    head = (head + 1) % capacity;
                    return x;
                }
            }
        }
    }

    public void printQueue(String type, String thread, String x) {
        System.out.println("\n" + type.toUpperCase() + "\nArray after Thread " + thread + " " + type + "d: " + x);
        for (int i = 0; i < capacity; i++) {
            System.out.print(queue[i] + " ");
        }
        System.out.println("");
    }
}

// ISSUES:
// 1. Array is used and not a linked list, so cannot throw new EmptyException.
// 2. Cannot lock both ends to allow one thread to print at a time
// 3. Head and tail variables not updating sequentially