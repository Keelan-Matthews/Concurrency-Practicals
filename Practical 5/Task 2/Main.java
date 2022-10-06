public class Main {
    public static void main(String[] args) {
        Gallery g = new Gallery();
        Security s[] = new Security[5];
        
        for (int i = 0; i < 5; i++) {
            s[i] = new Security(g);
            s[i].setName(Integer.toString(i));
            s[i].start();
        }
    }
}