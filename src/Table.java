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
    this.leftForkInUse = new boolean[seats];
    this.rightForkInUse = new boolean[seats];

    // Initialize philosophers
    for (int i = 0; i < seats; i++) {
      philosophers[i] = new Philosopher("Philosopher " + (i + 1), this);
    }

    // Initialize forks
    for (int i = 0; i < seats; i++) {
      forks[i] = new Object();
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

  public Object dropLeftFork(int philosopherIndex) {
    int leftForkIndex = philosopherIndex;
    leftForkInUse[leftForkIndex] = false;

    return forks[leftForkIndex];
  }

  public Object dropRightFork(int philosopherIndex) {
    int rightForkIndex = (philosopherIndex + 1) % seats;
    rightForkInUse[rightForkIndex] = false;

    return forks[rightForkIndex];
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
    // Handle deadlock by moving one philosopher to an empty table
    // Find the philosopher index with the leftmost fork in use
    int philosopherIndex = -1;
    for (int i = 0; i < seats; i++) {
      if (leftForkInUse[i]) {
        philosopherIndex = i;
        break;
      }
    }

    // Move the philosopher to an empty table (create a new thread for the philosopher)
    if (philosopherIndex != -1) {
      System.out.println("Moving philosopher " + (philosopherIndex + 1) + " to an empty table.");
      Thread newThread = new Thread(philosophers[philosopherIndex]);
      newThread.start();
    }
  }
}
