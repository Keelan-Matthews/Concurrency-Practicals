import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSQueue implements Lock{
    private AtomicReference<MCSNode> tail;
    private ThreadLocal<MCSNode> myNode;

    public MCSQueue(){
        tail = new AtomicReference<MCSNode>(null);
        myNode = new ThreadLocal<MCSNode>(){
            protected MCSNode initialValue(){
                return new MCSNode();
            }
        };
    }

    public void lock(){
        MCSNode node = myNode.get();
        MCSNode pred = tail.getAndSet(node);
        if(pred != null){
            node.locked = true;
            pred.next = node;
            while(node.locked){}
        }
    }

    public void unlock(){
        MCSNode node = myNode.get();
        if(node.next == null){
            if(tail.compareAndSet(node, null)){
                return;
            }
            while(node.next == null){}
        }
        node.next.locked = false;
        node.next = null;
    }

    private class MCSNode{
        private volatile boolean locked;
        private volatile MCSNode next;

        public MCSNode(){
            locked = false;
            next = null;
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
