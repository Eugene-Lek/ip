public class Todo {
    private String name;
    private String dueDate;

    Todo(String name, String dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
//        if (this.dueDate != null) {
//            return "[D] " +  this.name + "(by: " + this.dueDate + ")";
//        }
        return this.name;
    }
}
