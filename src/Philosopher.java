import java.util.Random;

/**
 * Philosopher
 * ------------
 * Defines the behaviour of a philosopher according to the protocol.
 */

public class Philosopher /* extends Thread */ {
  private String name;
  private Table table;

  // Constructor
  public Philosopher(String name, Table table) {
    this.name = name;
    this.table = table;
  }
  
  // Implement Philosopher protocol
  public void think() {
    try {
      // Think for a random amount of time (0 - 10 seconds)
      System.out.println(name + " thinks");
      Thread.sleep((int)(Math.random() * 10000));

      // After thinking, become hungry (i.e., stop thinking)
      pickUpFork();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void pickUpFork() {
    // Pick up fork with left hand first
    synchronized (table.getLeftFork()) {
      System.out.println(name + " picks up left fork");

      // Pick up fork with right hand
      // Else, wait until right fork is available.
      synchronized (table.getRightFork()) {
        System.out.println(name + " picks up right fork");

        eat();
      }
    }
  }

  public void eat() {
    try {
      // Eat for a random amount of time (0 - 5 seconds)
      System.out.println(name + " eats");
      Thread.sleep((int)(Math.random() * 5000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void putDownFork() {
    // After finished eating, put down left fork
    synchronized (table.dropLeftFork()) {
      System.out.println(name + " puts down left fork");

      // Then, put down right fork
      synchronized (table.dropRightFork()) {
        System.out.println(name + " puts down right fork");
      }
    }
    
  }

  // Repeat protocol infinitely until deadlock occurs
  public void startProtocol() throws InterruptedException {
    boolean deadlockDetected = false;

    while (!deadlockDetected) {
      think();
      pickUpFork();
      eat();
      putDownFork();

      // Periodically check if deadlock is detected
      deadlockDetected = table.isDeadlockDetected();
      if (deadlockDetected) {
        break;
      }
    }

    // TODO: Handle deadlock
    // Move one philosopher to empty table
  }
}
