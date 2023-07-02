/**
 * Table
 * ------------
 * Represents a table, 5 of which are occupied by philosophers
 * and the 6th unoccupied at the start.
 */

public class Table {
  private int seats;
  private Philosopher[] philosophers;
  private Object[] forks;
  private boolean[] leftForkInUse;
  private boolean[] rightForkInUse;

  // Constructor
  public Table(int seats) {
    this.seats = seats;
    this.philosophers = new Philosopher[seats];
    this.forks = new Object[seats];

    // Initialize philosophers
    for (int i = 0; i < seats; i++) {
      philosophers[i] = new Philosopher("Philosopher " + (i + 1), this);
    }
  }

  // Methods
  public Object getLeftFork(int philosopherIndex) {
    int leftForkIndex = philosopherIndex;
    leftForkInUse[leftForkIndex] = true;

    return forks[leftForkIndex];
  }

  public Object getRightFork(int philosopherIndex) {
    int rightForkIndex = (philosopherIndex + 1) % seats;
    rightForkInUse[rightForkIndex] = true;

    return forks[rightForkIndex];
  }

  public void dropLeftFork(int philosopherIndex) {
    int leftForkIndex = philosopherIndex;
    leftForkInUse[leftForkIndex] = false;
  }

  public void dropRightFork(int philosopherIndex) {
    int rightForkIndex = (philosopherIndex + 1) % seats;
    rightForkInUse[rightForkIndex] = false;
  }

  public boolean isDeadlockDetected() {
    // Implement deadlock detection logic
    // Check if all left forks are in use while at least one right fork is not in use
    for (int i = 0; i < seats; i++) {
      if (!leftForkInUse[i]) {
        return false; // Not all left forks are in use, no deadlock
      }
    }
    for (int i = 0; i < seats; i++) {
      if (!rightForkInUse[i]) {
        return true; // At least one right fork is not in use, potential deadlock
      }
    }
    return false; // No deadlock detected
  }

  public void movePhilosopher() {
  }
}
