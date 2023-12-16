### Zip and combine operator

The`zip`operator and the`combine`operator in Kotlin Flow are used to merge data from multiple flows.
They work differently when it comes to how they pair up the emitted values from these flows.

### Zip Operator:

The`zip`operator is like creating pairs of shoes from two rows of single shoes — one from the left
and one from the right. You can only make a pair if you have one shoe from each row. Similarly,`zip`
takes one emitted value from each flow and pairs them together into a new value. It waits until
there is a new value from both flows. If one flow emits values more quickly than the other,`zip`will
wait for the slower one before making a pair and moving on to the next set of values.

**When to Use Zip:**  
Use`zip`when you need to combine values from two flows where each pair of values correspond to each
other — like combining a user ID with a specific user name where the order and pairing matter.

### Combine Operator:

The`combine`operator is like a live scoreboard showing scores from two teams — whenever one team
scores, the board updates with that team’s latest score and the most recent score of the other
team.`combine`takes the latest value from one flow and combines it with the latest value from the
other flow, every time either flow emits a value. It doesn’t wait for a pair like`zip`does, and it
always uses the latest value from the other flow.

**When to Use Combine:**  
Use`combine`when you’re interested in getting the latest state of each flow to create a combined
state. It’s useful for things like UI updates where you need the current state of all observables,
like combining current temperature and humidity from two separate flows into a single weather status
flow which updates whenever either the temperature or humidity changes.

### Comparison:

- `zip`is synchronous and pair-wise. It only produces a value when it has a set of corresponding
  values, one from each flow.
- `combine`is asynchronous and more dynamic. It updates with any new emission from any of the flows
  using the latest value from the other.

### Choosing Between Zip and Combine:

- **Use`zip`**when you need to match each emission from multiple flows one-to-one. For example, when
  you’re combining a list of users with their respective avatars and each pairing is important.

- **Use`combine`**when you’re interested in the most current combination of values, and it’s okay to
  produce a new combined value whenever either flow has a new value. For instance, when you’re
  merging different sensor readings to get the most current status of your smart home system.
