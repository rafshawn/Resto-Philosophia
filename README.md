# Resto-Philosophia
Inspired by the Dining Philosophers problem

## Scenario
- Similar to the classical problem, but instead we have 5 tables with 5 philosophers each.
- A sixth table is present without any philosophers, but with the same arrangement as the other tables.

## Protocol
Each philosopher follows a specific protocol:
> Thinking --> Picking up fork --> Eating --> Setting down fork

## Deadlock
The concept of a deadlock in this case occurs when all the philosophers at a particular table are unable to acquire the necessary forks to eat. (i.e., when each philosopher is holding one fork **AND** waiting for another to become available).

Essentially, the protocol will run into a deadlock eventually if it runs long enough.
- If a deadlock occurs on a table, one philosopher drops fork they are holding and moves to a random unoccupied seat at the sixth table.
- Eventually the sixth table will have all its seats filled and also enter a deadlock.