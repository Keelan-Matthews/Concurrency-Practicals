import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSQueue implements Lock{
    private AtomicReference<MCSNode> tail;
    private ThreadLocal<MCSNode> myNode;
    private volatile int[] peopleQueue = new int[5];

    public MCSQueue(){
        tail = new AtomicReference<MCSNode>(null);
        myNode = new ThreadLocal<MCSNode>(){
            protected MCSNode initialValue(){
                return new MCSNode();
            }
        };
    }

    @Override
    public void lock(){
        MCSNode node = myNode.get();
        peopleQueue[node.getMarshal()]++;
        MCSNode pred = tail.getAndSet(node);
        if(pred != null){
            node.locked = true;
            pred.next = node;
            while(node.locked){}
        }
    }

    @Override
    public void unlock(){
        MCSNode node = myNode.get();
        if(node.next == null){
            if(tail.compareAndSet(node, null)){
                return;
            }
            while(node.next == null){}
        }

        // Print out queue
        MCSNode printNode = myNode.get().next;
        String output = "QUEUE: ";

        while(printNode != null){
            output = output + "{Marshal-"+printNode.getMarshal()+":Person "+peopleQueue[printNode.getMarshal()]+"} ";
            if (printNode.next != null) {
                output = output + " -> ";
            }
            printNode = printNode.next;
        }
        System.out.println(output);
        node.next.locked = false;
        node.next = null;
    }

    private class MCSNode{
        private volatile boolean locked;
        private volatile MCSNode next;
        private volatile String marshal;

        public MCSNode(){
            locked = false;
            next = null;
            marshal = Thread.currentThread().getName();
        }

        public int getMarshal(){
            return Integer.parseInt(marshal);
        }
    }

    public void lockInterruptibly() throws InterruptedException{
        throw new UnsupportedOperationException();
    }

    public boolean tryLock(){
        throw new UnsupportedOperationException();
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException{
        throw new UnsupportedOperationException();
    }

    public Condition newCondition(){
        throw new UnsupportedOperationException();
    }
}
