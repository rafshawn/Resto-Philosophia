import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlockTest {
  /* Test 1:
   * All left forks are in use and at least one right fork is not in use.
   */
  @Test
  public void testNoDeadlockDetected() {
    // Create a new table with 3 seats
    Table table = new Table(1, 3);

    // Set the left forks to be not in use
    table.getLeftForkInUse()[0] = false;
    table.getLeftForkInUse()[1] = false;
    table.getLeftForkInUse()[2] = false;

    // Set the right forks to be in use for the last two seats
    table.getRightForkInUse()[0] = false;
    table.getRightForkInUse()[1] = true;
    table.getRightForkInUse()[2] = true;

    // Check if there is a deadlock detected on the table
    boolean result1 = table.isDeadlockDetected(1);

    // Assert that no deadlock is detected
    assertFalse(result1);
  }

  /* Test 2:
   * All left forks are in use and all right forks are in use.
   */
  @Test
  public void testPotentialDeadlockDetected() {
    // Create a new table with 3 seats
    Table table = new Table(2, 3);

    // Set the left forks for all seats as in use
    table.getLeftForkInUse()[0] = true;
    table.getLeftForkInUse()[1] = true;
    table.getLeftForkInUse()[2] = true;

    // Set the right forks for all seats as in use
    table.getRightForkInUse()[0] = true;
    table.getRightForkInUse()[1] = true;
    table.getRightForkInUse()[2] = true;

    // Check if a deadlock is detected
    boolean result2 = table.isDeadlockDetected(2);

    // Assert that a deadlock is detected
    assertTrue(result2);
  }
}