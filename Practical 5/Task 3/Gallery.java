import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gallery {
    private Node head;

    public Gallery() {
        head = new Node("min", Integer.MIN_VALUE);
        head.next = new Node("max", Integer.MAX_VALUE);
    }

    public boolean add(String item, long timePeriod) {
        int key = item.hashCode();
        head.lock();
        Node pred = head;
        try {
            Node curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    return false;
                } 
                Node node = new Node(item, timePeriod);
                node.next = curr;
                pred.next = node;
                return true;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    public boolean remove(String item) {
        Node pred = null, curr = null;
        int key = item.hashCode();
        head.lock();
        try {
            pred = head;
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    pred.next = curr.next;
                    return true;
                } 
                return false;
            } finally {
                curr.unlock();
            }
        } finally {
            Node temp = head.next;
            System.out.print("Thread-" + Thread.currentThread().getName() + ": ");
            while (temp.next != null) {
                String sID = temp.item.substring(0,1);
                String person = temp.item.substring(1);
                if (Integer.parseInt(sID) == Integer.parseInt(Thread.currentThread().getName())) {
                    System.out.print("(P-" + person + ", " + (temp.timePeriod - System.currentTimeMillis()) + "ms) ");
                }
                temp = temp.next;
            }
            System.out.println("");
            pred.unlock();
        }
    }

    private class Node {
        String item;
        int key;
        long timePeriod;
        Node next;
        Lock lock = new ReentrantLock();

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
            this.timePeriod = timePeriod + System.currentTimeMillis();
        }

        public void lock() {
            lock.lock();
        }

        public void unlock() {
            lock.unlock();
        }
    }
}
