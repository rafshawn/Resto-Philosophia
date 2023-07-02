public class Main {
  public static void main(String[] args) {
    int numTables = 2;
    int numPhilosophers = 5; // # of philosophers per table

    // Create tables
    Table[] tables = new Table[numTables];
    for (int i = 0; i < numTables; i++) {
      tables[i] = new Table(i + 1, numPhilosophers);
    }

    // Create and start philosopher threads
    Thread[][] philosopherThreads = new Thread[numTables][numPhilosophers];
    for (int i = 0; i < numTables; i++) { 
      Table table = tables[i];
      for (int j = 0; j < numPhilosophers; j++) {
        String philosopherName = Character.toString((char) ('A' + j));
        Philosopher philosopher = new Philosopher("Philosopher " + philosopherName, table, i + 1, philosopherName);
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