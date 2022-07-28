import java.util.ArrayList;

public class Scrumboard {
    private ArrayList<String> Todo = new ArrayList<String>();
    private ArrayList<String> Completed = new ArrayList<String>();

    public Scrumboard() {
        Todo.add("A");
        Todo.add("B");
        Todo.add("C");
        Todo.add("D");
        Todo.add("E");
        Todo.add("F");
        Todo.add("G");
        Todo.add("H");
        Todo.add("I");
        Todo.add("J");
    }

    public String nextItem() {
        if (Todo.size() != 0) 
            return Todo.get(Todo.size()-1);

        return "No more todos left";
    }

    public void complete(String task) {
        Completed.add(task);

        if (Todo.size() != 0)
            Todo.remove(Todo.size()-1);
    }

    public int getSize() {
        return Todo.size();
    }
}