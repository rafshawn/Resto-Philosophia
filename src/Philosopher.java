import java.util.concurrent.locks.ReentrantLock;

/**
 * Philosopher
 * ------------
 * Defines the behaviour of a philosopher according to the protocol.
 */

public class Philosopher implements Runnable {
  private String name;
  private Table table;
  private ReentrantLock leftFork;
  private ReentrantLock rightFork;

  // Constructor
  public Philosopher(String name, Table table, int philosopherIndex) {
    this.name = name;
    this.table = table;
    this.leftFork = table.getLeftFork(philosopherIndex);
    this.rightFork = table.getRightFork(philosopherIndex);
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
        isDeadlocked = table.isDeadlockDetected();
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
    System.out.println(name + " picks up left fork");

    rightFork.lock();
    System.out.println(name + " picks up right fork");
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
    System.out.println(name + " puts down right fork");

    leftFork.unlock();
    System.out.println(name + " puts down left fork");
  }
}
