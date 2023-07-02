import java.util.concurrent.locks.ReentrantLock;

/**
 * Philosopher
 * ------------
 * Defines the behaviour of a philosopher according to the protocol.
 */

public class Philosopher implements Runnable {
  private String name;
  private Table table;
  private int tableNumber;
  private ReentrantLock leftFork;
  private ReentrantLock rightFork;

  // Constructor
  public Philosopher(String name, Table table, int tableNumber, String philosopherName) {
    this.name = name;
    this.table = table;
    this.tableNumber = tableNumber;
    int philosopherIndex = philosopherName.charAt(0) - 'A';
    this.leftFork = table.getLeftFork(philosopherName);
    this.rightFork = table.getRightFork(philosopherName);
  }

  @Override
  public void run() {
    boolean isDeadlocked = false;

    while (!isDeadlocked) {
      think();
      pickUpFork();
      eat();
      putDownFork();

      synchronized (table) {
        isDeadlocked = table.isDeadlockDetected(tableNumber);
        if (isDeadlocked) {
          break;
        }
      }
    }

    // Handle deadlock by moving one philosopher to empty table
    table.movePhilosopher();
  }

  public void think() {
    try {
      // Think for a random duration (0 - 10 seconds)
      System.out.println(name + " is thinking");
      Thread.sleep((int)(Math.random() * 10000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public void pickUpFork() {
    leftFork.lock();
    System.out.println(name + " picks up left fork from table " + tableNumber);

    rightFork.lock();
    System.out.println(name + " picks up right fork from table " + tableNumber);
  }
  
  public void eat() {
    try {
      // Eat for a random duration (0 - 5 seconds)
      System.out.println(name + " is eating");
      Thread.sleep((int)(Math.random() * 5000));
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  public void putDownFork() {
    rightFork.unlock();
    System.out.println(name + " puts down right fork from table " + tableNumber);

    leftFork.unlock();
    System.out.println(name + " puts down left fork from table " + tableNumber);
  }
}
