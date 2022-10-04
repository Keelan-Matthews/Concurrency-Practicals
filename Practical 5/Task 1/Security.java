public class Security extends Thread {
    private Gallery g;
    private long time;

    public Security(Gallery g) {
        this.g = g;
        this.time = System.currentTimeMillis();
    }

    @Override
    public void run() {
        // loop until "process" = 10
        // if time+200 < System.currentTimeMillis() add person to gallery (make sure haven't added more than 10)
        // generate curr time + random time and add to an array (index is persons index) then add person and pass in item (unique) -- 1 array for items and 1 array for times
        // else go through array (0 to num of ppl) and check if time[i] < System.currentTimeMillis() remove that person

        // String sID = temp.item.substring(0,2);
        //         String person = temp.item.substring(2);
        //         System.out.print("Thread " + sID + " added (P-" + person + ", " + (temp.timePeriod - System.currentTimeMillis()) + "ms)");
    }
}
