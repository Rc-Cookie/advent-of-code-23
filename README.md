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
Duration (average of 100 runs): 105.036ms

┌────────╥──────────┬──────────┐
│        ║   Task 1 │   Task 2 │
╞════════╬══════════╪══════════╡
│  Day 1 ║  0.310ms │  0.551ms │
├────────╫──────────┼──────────┤
│  Day 2 ║  0.842ms │  0.620ms │
├────────╫──────────┼──────────┤
│  Day 3 ║  0.142ms │  0.085ms │
├────────╫──────────┼──────────┤
│  Day 4 ║  0.193ms │  0.113ms │
├────────╫──────────┼──────────┤
│  Day 5 ║  0.050ms │  0.081ms │
├────────╫──────────┼──────────┤
│  Day 6 ║  0.003ms │  0.002ms │
├────────╫──────────┼──────────┤
│  Day 7 ║  0.159ms │  0.162ms │
├────────╫──────────┼──────────┤
│  Day 8 ║  0.074ms │  0.182ms │
├────────╫──────────┼──────────┤
│  Day 9 ║  0.108ms │  0.063ms │
├────────╫──────────┼──────────┤
│ Day 10 ║  0.078ms │  0.099ms │
├────────╫──────────┼──────────┤
│ Day 11 ║  0.074ms │  0.076ms │
├────────╫──────────┼──────────┤
│ Day 12 ║  1.180ms │ 22.463ms │
├────────╫──────────┼──────────┤
│ Day 13 ║  0.056ms │  0.022ms │
├────────╫──────────┼──────────┤
│ Day 14 ║  0.068ms │ 10.402ms │
├────────╫──────────┼──────────┤
│ Day 15 ║  0.059ms │  0.210ms │
├────────╫──────────┼──────────┤
│ Day 16 ║  0.108ms │ 29.985ms │
├────────╫──────────┼──────────┤
│ Day 17 ║ 15.261ms │ 20.919ms │
├────────╫──────────┼──────────┤
│ Day 18 ║  0.009ms │  0.011ms │
├────────╫──────────┼──────────┤
│ Day 19 ║  0.160ms │  0.055ms │
└────────╨──────────┴──────────┘
```
