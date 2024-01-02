package Entity;

public class Depot extends Client {
    public Depot(String id, int x, int y, int readyTime, int dueTime) {
        super(id, x, y,  readyTime, dueTime, 0, 0);
    }
}
