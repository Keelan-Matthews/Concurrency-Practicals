import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSQueue implements Lock{
    private MCSNode tail;
    private ThreadLocal<MCSNode> myNode;

    public MCSQueue(){
        tail = null;
        myNode = new ThreadLocal<MCSNode>(){
            protected MCSNode initialValue(){
                return new MCSNode();
            }
        };
    }

    public void lock(){
        MCSNode node = myNode.get();
        node.locked = true;
        MCSNode pred = tail;
        if(pred != null){
            pred.next = node;
            while(node.locked){
                //spin
            }
        }
    }

    public void unlock(){
        MCSNode node = myNode.get();
        if(node.next == null){
            if(tail == node){
                if(tail.compareAndSet(node, null)){
                    return;
                }
                while(node.next == null){
                    //spin
                }
            }
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
