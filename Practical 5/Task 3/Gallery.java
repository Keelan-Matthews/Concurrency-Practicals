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
        while (true) {
            Node pred = head;
            Node curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (curr.key == key) {
                        return false;
                    } else {
                        Node node = new Node(item, timePeriod);
                        node.next = curr;
                        pred.next = node;
                        return true;
                    }
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
        }
    }

    public boolean remove(String item) {
        int key = item.hashCode();
        while (true) {
            Node pred = head;
            Node curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (curr.key == key) {
                        pred.next = curr.next;
                        return true;
                    } else {
                        return false;
                    }
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
                curr.unlock();
            }
        }
    }

    private boolean validate(Node pred, Node curr) {
        Node node = head;
        while (node.key <= pred.key) {
            if (node == pred) {
                return pred.next == curr;
            }
            node = node.next;
        }
        return false;
    }

    public boolean contains(String item) {
        int key = item.hashCode();
        while(true) {
            Node pred = this.head;
            Node curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    return curr.key == key;
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
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
