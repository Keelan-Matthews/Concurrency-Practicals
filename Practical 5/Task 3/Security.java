import java.util.Vector;

public class Security extends Thread {
    private Gallery g;
    private long time;
    private int completed = 0;
    private int inGallery = 0;
    private Vector<Long> enterTimes = new Vector<Long>();
    private Vector<String> enterItems = new Vector<String>();

    public Security(Gallery g) {
        this.g = g;
        this.time = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (completed < 10) {
            if (System.currentTimeMillis() - time >= 200 && inGallery < 10) {
                time = System.currentTimeMillis();

                long timePeriod = (long)(Math.random() * 900) + 100;
                String item = Thread.currentThread().getName() + Integer.toString(inGallery);
                g.add(item, timePeriod);

                String sID = item.substring(0,1);
                String person = item.substring(1);
                System.out.println("Thread " + sID + " added (P-" + person + ", " + timePeriod + "ms)");

                enterTimes.add(System.currentTimeMillis() + timePeriod);
                enterItems.add(item);
                inGallery++;
            }
            else {
                for (int i = 0; i < enterItems.size(); i++) {
                    if (enterTimes.get(i) < System.currentTimeMillis()) {
                        System.out.println("");
                        System.out.println("Current Gallery:");

                        if (g.remove(enterItems.get(i))) {
                            enterTimes.remove(i);
                            enterItems.remove(i);
                            completed++;
                        }
                    }
                }
            }
        }
    }
}
