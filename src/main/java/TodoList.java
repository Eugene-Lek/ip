import java.util.ArrayList;

public class TodoList {
    private final ArrayList<Todo> todos;

    TodoList() {
        this.todos = new ArrayList<Todo>();
    }

    public void addTodo(String name, String dueDate) {
        this.todos.add(new Todo(name, dueDate));
    }


    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < todos.size(); i++) {
            Todo todo = this.todos.get(i);
            String todoString = (i+1) + ". " + todo.toString() + "\n";
            display.append(todoString);
        }

        return display.toString();
    }
}
