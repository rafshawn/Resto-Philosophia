import java.util.List;
import java.util.ArrayList;

public class Main {
  private static int numTables;

  public static int getNumTables() {
    return numTables;
  }

  public static void main(String[] args) {
    numTables = 2; // # of tables (including empty table)
    int numPhilosophers = 5; // # of philosophers per table

    // Create tables
    List<Table> tables = new ArrayList<>();
    for (int i = 0; i < numTables; i++) {
      tables.add(new Table(i + 1, numPhilosophers));
    }

    // Create and start philosopher threads
    List<List<Thread>> philosopherThreads = new ArrayList<>();
    for (int i = 0; i < numTables; i++) { 
      Table table = tables.get(i);
      List<Thread> threads = new ArrayList<>();
      for (int j = 0; j < numPhilosophers; j++) {
        String philosopherName = Character.toString((char) ('A' + j));
        Philosopher philosopher = new Philosopher("Philosopher " + philosopherName, table, i + 1, philosopherName);
        Thread thread = new Thread(philosopher);
        threads.add(thread);
        thread.start();
      }
      philosopherThreads.add(threads);
    }

    // Wait for philosopher threads to complete
    try {
      for (List<Thread> threads : philosopherThreads) {
        for (Thread thread : threads) {
          thread.join();
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}