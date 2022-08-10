public class Main {
    public static void main(String[] args) {
	    Transport[] buses = new Transport[5];

        Venue destination = new Venue();

        for(int i = 0; i < 5; i++)
            buses[i] = new Transport(destination);

        for(Transport bus : buses)
            bus.start();

    }
}
