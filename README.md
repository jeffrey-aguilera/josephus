# josephus

This coding exercise solves a variant of the [Josephus problem](https://en.wikipedia.org/wiki/Josephus_problem).

A circle of `n` elements is iteratively reduced to a single element by discarding every `k`-th element.
The initial elements of the circle are labelled by their ordinal position `1` to `n` in the direction of counting.

For example, suppose that `n` is 4 and `k` is 2. The initial circle can be represented as

| 1 | 2 | 3 | 4 |
|---|---|---|---|

A cursor is imagined to exist _between_ the elements of the circle.
If the cursor is initially located to the left of element `1`, this extended circle can be represented as:

| * | 1 | 2 | 3 | 4 |
|---|---|---|---|---|

where `*` signifies the cursor.

The first step for `n = 4` and `k = 2` is the advancement of the cursor by two steps:

| 1 | 2 | * | 3 | 4 |
|---|---|---|---|---|

and then the removal of the element _before_ the cursor:

| 1 | * | 3 | 4 |
|---|---|---|---|

Advancing two more:

| 1 | 3 | 4 | * |
|---|---|---|---|

Removing element before the cursor:

| 1 | 3 | * |
|---|---|---|

Now, since this is a circular data structure, this is equivalent to:

| * | 1 | 3 |
|---|---|---|

and when we advance two more steps (the size of the remaining circle), we are back to:

| 1 | 3 | * |
|---|---|---|

_i.e._, nothing has changed.

After deleting the element before the cursor, the iterative reduction is complete:

| 1 | * |
|---|---|

Thus, `Survivor(4, 2) = 1`.

## Running

    sbt "run size step"

where `size` is the initial size of the circle and `step` is the fixed number of positions to count off starting at
zero.
