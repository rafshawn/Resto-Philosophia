import java.util.concurrent.locks.ReentrantLock;

/**
 * Table
 * ------------
 * Represents a table, 5 of which are occupied by philosophers
 * and the 6th unoccupied at the start.
 */

public class Table {
  private int tableNumber;
  private int seats;
  private Philosopher[] philosophers;
  private ReentrantLock[] forks;
  private boolean[] leftForkInUse;
  private boolean[] rightForkInUse;

  // Constructor
  public Table(int tableNumber, int seats) {
    this.tableNumber = tableNumber;
    this.seats = seats;
    this.philosophers = new Philosopher[seats];
    this.forks = new ReentrantLock[seats];
    this.leftForkInUse = new boolean[seats];
    this.rightForkInUse = new boolean[seats];

    // Initialize philosophers and forks
    for (int i = 0; i < seats; i++) {
      String philosopherName = Character.toString((char) ('A' + i));
      philosophers[i] = new Philosopher("Philosopher " + philosopherName, this, tableNumber, philosopherName);
      forks[i] = new ReentrantLock();
      leftForkInUse[i] = false;
      rightForkInUse[i] = false;
    }
  }

  // Methods
  public ReentrantLock getLeftFork(String philosopherLetter) {
    int philosopherIndex = philosopherLetter.charAt(0) - 'A';
    int leftForkIndex = philosopherIndex;
    leftForkInUse[leftForkIndex] = true;

    return forks[leftForkIndex];
  }

  public ReentrantLock getRightFork(String philosopherLetter) {
    int philosopherIndex = philosopherLetter.charAt(0) - 'A';
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

  public boolean isDeadlockDetected(int tableNumber) {
    // Implement deadlock detection logic
    for (int i = 0; i < seats; i++) {
      int leftForkIndex = i;
      int rightForkIndex = (i + 1) % seats;
      int nextPhilosopherIndex = (i + 1) % seats;

      // Check if the philosopher is holding the left fork and the right fork is already in use by the next philosopher
      if (leftForkInUse[leftForkIndex] && rightForkInUse[rightForkIndex] && leftForkInUse[nextPhilosopherIndex]) {
        System.out.println("Deadlock detected in table " + tableNumber + ".\n");

        return true;
      }
    }

    System.out.println("No deadlock detected in table" +  tableNumber + ".\n");

    return false;
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
      String philosopherName = Character.toString((char) ('A' + philosopherIndex));
      System.out.println("Moving philosopher " + philosopherName + " to an empty table.");

      // Release the forks in the original table
      dropLeftFork(philosopherIndex);
      dropRightFork(philosopherIndex);

      // Start the thread for the moved philosopher in the empty table
      philosophers[philosopherIndex] = new Philosopher("Philosopher " + philosopherName, this, tableNumber, philosopherName);
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
