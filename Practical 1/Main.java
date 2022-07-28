public class Main {
    public static void main(String[] args){
        Scrumboard s = new Scrumboard();
        ThreadClass t1 = new ThreadClass("Thread-1",s);
        ThreadClass t2 = new ThreadClass("Thread-2",s);

        t1.start();
        t2.start();
    }
}
