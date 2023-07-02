import java.util.Random;

/**
 * Philosopher
 * ------------
 * Defines the behaviour of a philosopher according to the protocol.
 */

public class Philosopher implements Runnable {
  private String name;
  private Table table;

  // Constructor
  public Philosopher(String name, Table table) {
    this.name = name;
    this.table = table;
  }

@Override
public void run() {
  try {
    boolean isDeadlocked = false;

    while (!isDeadlocked) {
      think();
      pickUpFork();
      eat();
      putDownFork();

      isDeadlocked = table.isDeadlockDetected();
      if (isDeadlocked) {
        break;
      }
    }

    // Handle deadlock by moving one philosopher to empty table
    table.movePhilosopher();

  } catch (InterruptedException e) {
    e.printStackTrace();
  }
}
  
public void think() {
  try {
    System.out.println(name + " is thinking");
    Thread.sleep((int)(Math.random() * 10000));
    pickUpFork();
  } catch (InterruptedException e) {
    e.printStackTrace();
  }
}

public void pickUpFork() {
  synchronized (table.getLeftFork()) {
    System.out.println(name + " picks up left fork");

    synchronized (table.getRightFork()) {
      System.out.println(name + " picks up right fork");

      eat();
    }
  }
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
    synchronized (table.dropLeftFork()) {
      System.out.println(name + " puts down left fork");

      synchronized (table.dropRightFork()) {
        System.out.println(name + " puts down right fork");
      }
    }
  }
}
