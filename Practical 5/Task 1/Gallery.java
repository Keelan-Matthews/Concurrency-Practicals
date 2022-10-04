import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gallery {
    private Node head;
    private Lock lock = new ReentrantLock();

    public Gallery() {
        head = new Node("min", Integer.MIN_VALUE);
        head.next = new Node("max", Integer.MAX_VALUE);
    }

    public boolean add(String item, long timePeriod) {
        Node pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                return false;
            } else {
                Node node = new Node(item, timePeriod);
                node.next = curr;
                pred.next = node;
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(String item) {
        Node pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else {
                return false;
            }
        } finally {
            // print list
            Node temp = head.next;
            while (temp.next != null) {
                // print
                temp = temp.next;
            }
            lock.unlock();
        }
    }

    private class Node {
        String item;
        int key;
        long timePeriod;
        Node next;

        public Node(String item, long timePeriod) {
            this.item = item;
            this.next = null;
            if (item.equals("min")) {
                this.key = Integer.MIN_VALUE;
            } else if (item.equals("max")) {
                this.key = Integer.MAX_VALUE;
            } else {
                this.key = item.hashCode();
            }
            this.timePeriod = timePeriod;
        }
    }
}
