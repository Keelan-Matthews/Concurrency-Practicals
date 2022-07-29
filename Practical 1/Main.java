public class Main {
    public static void main(String[] args){
        Peterson lock = new Peterson();
        Scrumboard s = new Scrumboard();
        ThreadClass t1 = new ThreadClass(s, lock);
        ThreadClass t2 = new ThreadClass(s, lock);

        t1.start();
        t2.start();
    }
}
