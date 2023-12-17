# Advent of Code 2023

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2023.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) package
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

My main solutions are (mostly) optimized for performance.
Thus, they avoid using things like `String.split()` or creating substrings at all, resulting in some 'interesting' looking parsing methods.
Additionally, I make use of some bit shift magic for some puzzles.
All of this doesn't really improve readability and also increases size, thus I have started another 'clean' solution, which has its own config file and solution classes.
They are generally a lot easier to read and use similar algorithms, but are quite a bit slower than the code spagetti.

## Performance

Some performance stats of the optimized solutions, which have been obtained using `--all --warmup 100 --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 101.682ms

┌────────╥──────────┬──────────┐
│        ║   Task 1 │   Task 2 │
╞════════╬══════════╪══════════╡
│  Day 1 ║  0.300ms │  0.579ms │
├────────╫──────────┼──────────┤
│  Day 2 ║  0.757ms │  0.618ms │
├────────╫──────────┼──────────┤
│  Day 3 ║  0.148ms │  0.099ms │
├────────╫──────────┼──────────┤
│  Day 4 ║  0.158ms │  0.111ms │
├────────╫──────────┼──────────┤
│  Day 5 ║  0.052ms │  0.128ms │
├────────╫──────────┼──────────┤
│  Day 6 ║  0.004ms │  0.002ms │
├────────╫──────────┼──────────┤
│  Day 7 ║  0.143ms │  0.129ms │
├────────╫──────────┼──────────┤
│  Day 8 ║  0.073ms │  0.177ms │
├────────╫──────────┼──────────┤
│  Day 9 ║  0.077ms │  0.061ms │
├────────╫──────────┼──────────┤
│ Day 10 ║  0.072ms │  0.098ms │
├────────╫──────────┼──────────┤
│ Day 11 ║  0.121ms │  0.071ms │
├────────╫──────────┼──────────┤
│ Day 12 ║  1.228ms │ 21.352ms │
├────────╫──────────┼──────────┤
│ Day 13 ║  0.087ms │  0.102ms │
├────────╫──────────┼──────────┤
│ Day 14 ║  0.067ms │  9.877ms │
├────────╫──────────┼──────────┤
│ Day 15 ║  0.060ms │  0.268ms │
├────────╫──────────┼──────────┤
│ Day 16 ║  0.098ms │ 28.758ms │
├────────╫──────────┼──────────┤
│ Day 17 ║ 14.801ms │ 21.008ms │
└────────╨──────────┴──────────┘
```
