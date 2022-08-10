public class Main {
    public static void main(String[] args) {
        int n = 5;
	    Transport[] buses = new Transport[n];
        Filter lock = new Filter(n);

        Venue destination = new Venue();

        for(int i = 0; i < 5; i++)
            buses[i] = new Transport(destination, lock);

        for(Transport bus : buses)
            bus.start();

    }
}
