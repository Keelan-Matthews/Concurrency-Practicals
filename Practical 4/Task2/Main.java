public class Main {
    public static void main(String args[]){
        VotingStation vs = new VotingStation();
        Marshal m[] = new Marshal[5];

        for (int i = 0; i < 5; i++) {
            m[i] = new Marshal(vs);
            m[i].setName(Integer.toString(i));
            m[i].start();
        }
    }
}
