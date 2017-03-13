# josephus

This coding exercise solves a variant of the [Josephus problem](https://en.wikipedia.org/wiki/Josephus_problem).

A circle of `n` elements is iteratively reduced to a single element by discarding every `k`-th element.
The initial elements of the circle are labelled by their ordinal positions `1` to `n` in the direction of counting.

For example, suppose that `n` is 4 and `k` is 2. The initial elements in the circle can be represented as

| 1 | 2 | 3 | 4 |
|---|---|---|---|

A cursor is imagined to exist _between_ the elements, initially located to the left of the first element.
This extended circle can be visualized as:

| * | 1 | 2 | 3 | 4 |
|---|---|---|---|---|

where `*` signifies the cursor.

The first move for `k = 2` is the advancement of the cursor by two positions:

| 1 | 2 | * | 3 | 4 |
|---|---|---|---|---|

and then the removal of the element _before_ the cursor:

| 1 | * | 3 | 4 |
|---|---|---|---|

(Alternatively, we could advance the cursor by `k - 1` positions and then remove the element _after_ the cursor.)

Advancing two more positions:

| 1 | 3 | 4 | * |
|---|---|---|---|

Removing element before the cursor:

| 1 | 3 | * |
|---|---|---|

Now, since this is a circular data structure, this is equivalent to:

| * | 1 | 3 |
|---|---|---|

and when we advance two more positions (which happens to be the size of the remaining circle), we are back to:

| 1 | 3 | * |
|---|---|---|

_i.e._, nothing has changed.

After deleting the element before the cursor, the iterative reduction is complete:

| 1 | * |
|---|---|

Thus, `Survivor(4, 2) = 1`.

Two trivial cases stand out: `k = 0` and `k = 1`.

When `k = 0`, the cursor never advances, so the highest numbered element is removed at each step.
Hence, `Survivor(n, 0) = 1`.

When `k = 1`, the cursor advances over the lowest numbered element, which is then deleted.
Hence  `Survivor(n, 1) = n`.

It is also easy to show that `Survivor(n, n) = Survivor(n - 1, n) = 1 + Survivor(n - 2, n)`

## Simulated Solution

To simulate a solution to this problem, start with a `Circle` of labelled ordinal positions from `1` to `n`.
The simplest means of reducing this to a smaller problem parallels the example above by iterative reduction to the next
smaller circle with special care to keep the cursor fixed before the first element of the `Circle`:

 * let `k' = k mod n` be the effective step
 * if `k'` is zero, generate a new `Circle` from the first `n - 1` elements
 * otherwise, move the first `k - 1` elements to the end and skip the `k`-th element

This `Circle` is used by the `NaiveSurvivor` class to find the survivor index.
The special cases noted above are used for testing, but not by `NaiveSurvivor` directly.

The problem with this solution is that the shuffling needed to keep the cursor at the beginning of the circular list
is very expensive.

Nevertheless, this constitutes a reference implementation.

## Improved Solution

If `k ≤ n`, we can perform multiple deletions in one step.
The trick is to partition the elements of the circle into groups of size `k` and then drop the last element of each group.
The tricky part occurs when the last group contains fewer than `k` elements.

`FastSurvivor` uses this technique with `Circle`.
For very large `n`, the performance difference is not as large as expected:

        NaiveSurvivor(Range 10000000 to 10000000, Range 2 to 2) took 11054 ms
        FastSurvivor(Range 10000000 to 10000000, Range 2 to 2) took 4151 ms

## Recursive Solution

Simulating the motion of a `Circle` to solve this problem is clearly overkill.
`Survivor(n, k)` can be solved in terms of the next smaller problem by merely relabelling the indexes.

| 1     | 2     | ... | k-1 | k       | k+1 | ... | n   |
|-------|-------|-----|-----|---------|-----|-----|-----|
| n-k+1 | n-k+2 | ... | n-1 | deleted |  1  | ... | n-k |

That is,

    Survivor(n, k) = Survivor(n-1, k) + k,          if Survivor(n-1, k) <= n - k
    Survivor(n, k) = Survivor(n-1, k) + k - n,      if Survivor(n-1, k) > n - k

or

    Survivor(1, k) = 1
    Survivor(n, k) = ((Survivor(n-1, k) + k - 1) mod n) + 1

This is a primitive recursive formula, so it is guaranteed to terminate; but it is not tail recursive.
Consequently, this formula is expected to stack overflow prematurely.
This can be avoided by converting the recursion to an iteration.
After some manipulation and legerdemain, this reduces to

    (1 to size).fold(1) {
      case (value, n) =>
        ((value + step - 1) mod n) + 1
    }

which produces the same results as the reference implementation, but with much less time and bounded memory.

## Running

    sbt "run size step"

where `size` is the initial size of the circle and `step` is the fixed number of positions to count off starting at
zero. `size` should be positive; and `step` should be non-negative.
