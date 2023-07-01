/**
 * Table
 * ------------
 * Represents a table, 5 of which are occupied by philosophers
 * and the 6th unoccupied at the start.
 */

public class Table {
  private int seats;
  private Philosopher[] philosophers;

  // Constructor
  public Table(int seats) {
    this.seats = seats;
    this.philosophers = new Philosopher[seats];
  }

  // Methods
  public Object getLeftFork() {
    return null;
  }

  public Object getRightFork() {
    return null;
  }

  public Object dropLeftFork() {
    return null;
  }

  public Object dropRightFork() {
    return null;
  }

  public boolean isDeadlockDetected() {
    return false;
  }
}
