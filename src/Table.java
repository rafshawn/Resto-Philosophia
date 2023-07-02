import java.util.concurrent.locks.ReentrantLock;

/**
 * Table
 * ------------
 * Represents a table, 5 of which are occupied by philosophers
 * and the 6th unoccupied at the start.
 */

public class Table {
  private int seats;
  private Philosopher[] philosophers;
  private ReentrantLock[] forks;
  private boolean[] leftForkInUse;
  private boolean[] rightForkInUse;

  // Constructor
  public Table(int seats) {
    this.seats = seats;
    this.philosophers = new Philosopher[seats];
    this.forks = new ReentrantLock[seats];
    this.leftForkInUse = new boolean[seats];
    this.rightForkInUse = new boolean[seats];

    // Initialize philosophers and forks
    for (int i = 0; i < seats; i++) {
      philosophers[i] = new Philosopher("Philosopher " + (i + 1), this, i);
      forks[i] = new ReentrantLock();
      leftForkInUse[i] = false;
      rightForkInUse[i] = false;
    }
  }

  // Methods
  public ReentrantLock getLeftFork(int philosopherIndex) {
    int leftForkIndex = philosopherIndex;
    leftForkInUse[leftForkIndex] = true;

    return forks[leftForkIndex];
  }

  public ReentrantLock getRightFork(int philosopherIndex) {
    int rightForkIndex = (philosopherIndex + 1) % seats;
    rightForkInUse[rightForkIndex] = true;

    return forks[rightForkIndex];
  }

  public ReentrantLock dropLeftFork(int philosopherIndex) {
    int leftForkIndex = philosopherIndex;
    leftForkInUse[leftForkIndex] = false;

    return forks[leftForkIndex];
  }

  public ReentrantLock dropRightFork(int philosopherIndex) {
    int rightForkIndex = (philosopherIndex + 1) % seats;
    rightForkInUse[rightForkIndex] = false;

    return forks[rightForkIndex];
  }

  public boolean isDeadlockDetected() {
    // Implement deadlock detection logic
    for (int i = 0; i < seats; i++) {
      int leftForkIndex = i;
      int rightForkIndex = (i + 1) % seats;
      int nextPhilosopherIndex = (i + 1) % seats;

      // Check if the philosopher is holding the left fork and the right fork is already in use by the next philosopher
      if (leftForkInUse[leftForkIndex] && rightForkInUse[rightForkIndex] && leftForkInUse[nextPhilosopherIndex]) {
        return true; // Deadlock detected
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

  // Getters
  public boolean[] getLeftForkInUse() {
    return leftForkInUse;
  }

  public boolean[] getRightForkInUse() {
    return rightForkInUse;
  }

  public int getSeats() {
    return seats;
  }
  
}
