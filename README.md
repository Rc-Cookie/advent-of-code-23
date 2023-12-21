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
Duration (average of 100 runs): 105.189ms

┌────────╥──────────┬──────────┐
│        ║   Task 1 │   Task 2 │
╞════════╬══════════╪══════════╡
│  Day 1 ║  0.339ms │  0.705ms │
├────────╫──────────┼──────────┤
│  Day 2 ║  0.687ms │  0.629ms │
├────────╫──────────┼──────────┤
│  Day 3 ║  0.144ms │  0.094ms │
├────────╫──────────┼──────────┤
│  Day 4 ║  0.161ms │  0.176ms │
├────────╫──────────┼──────────┤
│  Day 5 ║  0.051ms │  0.134ms │
├────────╫──────────┼──────────┤
│  Day 6 ║  0.004ms │  0.002ms │
├────────╫──────────┼──────────┤
│  Day 7 ║  0.136ms │  0.135ms │
├────────╫──────────┼──────────┤
│  Day 8 ║  0.060ms │  0.176ms │
├────────╫──────────┼──────────┤
│  Day 9 ║  0.077ms │  0.063ms │
├────────╫──────────┼──────────┤
│ Day 10 ║  0.085ms │  0.096ms │
├────────╫──────────┼──────────┤
│ Day 11 ║  0.203ms │  0.073ms │
├────────╫──────────┼──────────┤
│ Day 12 ║  1.266ms │ 22.000ms │
├────────╫──────────┼──────────┤
│ Day 13 ║  0.092ms │  0.027ms │
├────────╫──────────┼──────────┤
│ Day 14 ║  0.066ms │ 10.004ms │
├────────╫──────────┼──────────┤
│ Day 15 ║  0.061ms │  0.267ms │
├────────╫──────────┼──────────┤
│ Day 16 ║  0.102ms │ 29.341ms │
├────────╫──────────┼──────────┤
│ Day 17 ║ 15.262ms │ 21.043ms │
├────────╫──────────┼──────────┤
│ Day 18 ║  0.009ms │  0.010ms │
├────────╫──────────┼──────────┤
│ Day 19 ║  0.170ms │  0.055ms │
├────────╫──────────┼──────────┤
│ Day 20 ║  0.248ms │  0.935ms │
└────────╨──────────┴──────────┘
```
