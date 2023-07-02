/*
 * TODO:
 * - Initialize tables and philosophers
 * - Coordinate their actions
 * - Check for deadlock
 * - Start simulation
 */

public class Main {
  public static void main(String[] args) {
    int numPhilosophers = 5; // Number of philosophers
    Table table = new Table(numPhilosophers); // Create the table

    // Create and start philosopher threads
    Thread[] philosopherThreads = new Thread[numPhilosophers];
    for (int i = 0; i < numPhilosophers; i++) {
      Philosopher philosopher = new Philosopher("Philosopher " + (i + 1), table);
      philosopherThreads[i] = new Thread(philosopher);
      philosopherThreads[i].start();
    }

    // Wait for philosopher threads to complete
    try {
      for (Thread philosopherThread : philosopherThreads) {
        philosopherThread.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}