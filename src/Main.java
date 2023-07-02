public class Main {
  public static void main(String[] args) {
    int numTables = 2;
    int numPhilosophers = 5; // # of philosophers per table

    // Create tables
    Table[] tables = new Table[numTables];
    for (int i = 0; i < numTables; i++) {
      tables[i] = new Table(numPhilosophers);
    }

    // Create and start philosopher threads
    Thread[][] philosopherThreads = new Thread[numTables][numPhilosophers];
    for (int i = 0; i < numTables; i++) { 
      Table table = tables[i];
      for (int j = 0; j < numPhilosophers; j++) {
        Philosopher philosopher = new Philosopher("Philosopher " + (j + 1), table, j);
        philosopherThreads[i][j] = new Thread(philosopher);
        philosopherThreads[i][j].start();
      }
    }

    // Wait for philosopher threads to complete
    try {
      for (int i = 0; i < numTables; i++) {
        for (int j = 0; j < numPhilosophers; j++) {
          philosopherThreads[i][j].join();
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}