import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Timeout implements Lock {
    private QNode AVAILABLE = new QNode();
    private AtomicReference<QNode> tail;
    private ThreadLocal<QNode> myNode;
    private volatile int[] peopleQueue = new int[5];

    public Timeout() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        QNode node = new QNode();
        myNode.set(node);
        peopleQueue[node.getMarshal()]++;

        node.pred = null;
        QNode pred = tail.getAndSet(node);

        if (pred == null || pred.pred == AVAILABLE) {
            return true;
        }

        while (System.currentTimeMillis() - startTime < patience) {
            if (pred.pred == AVAILABLE) {
                return true;
            } else if (pred.pred != null) {
                pred = pred.pred;
            }
        }

        if (!tail.compareAndSet(node, pred)) {
            node.pred = pred;
        }

        return false;
    }

    public void unlock() {
        QNode node = myNode.get();
        if (!tail.compareAndSet(node, null))
            node.pred = AVAILABLE;

        QNode printNode = myNode.get().pred;
        String output = "QUEUE: ";

        while (printNode != null) {
            output = output + "{Marshal-" + printNode.getMarshal() + ":Person " + peopleQueue[printNode.getMarshal()]
                    + "} ";
            if (printNode.pred != null) {
                output = output + " -> ";
            }
            printNode = printNode.pred;
        }
        System.out.println(output);
    }

    private class QNode {
        private volatile QNode pred;
        private volatile String marshal;

        public QNode() {
            pred = null;
            marshal = Thread.currentThread().getName();
        }

        public int getMarshal() {
            return Integer.parseInt(marshal);
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public boolean tryLock() {
        throw new UnsupportedOperationException();
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    public void lock() {
        throw new UnsupportedOperationException();
    }
}
