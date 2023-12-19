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
Duration (average of 100 runs): 105.72ms

┌────────╥──────────┬──────────┐
│        ║   Task 1 │   Task 2 │
╞════════╬══════════╪══════════╡
│  Day 1 ║  0.333ms │  0.646ms │
├────────╫──────────┼──────────┤
│  Day 2 ║  0.738ms │  0.622ms │
├────────╫──────────┼──────────┤
│  Day 3 ║  0.151ms │  0.161ms │
├────────╫──────────┼──────────┤
│  Day 4 ║  0.183ms │  0.134ms │
├────────╫──────────┼──────────┤
│  Day 5 ║  0.051ms │  0.103ms │
├────────╫──────────┼──────────┤
│  Day 6 ║  0.004ms │  0.002ms │
├────────╫──────────┼──────────┤
│  Day 7 ║  0.160ms │  0.143ms │
├────────╫──────────┼──────────┤
│  Day 8 ║  0.072ms │  0.186ms │
├────────╫──────────┼──────────┤
│  Day 9 ║  0.080ms │  0.061ms │
├────────╫──────────┼──────────┤
│ Day 10 ║  0.090ms │  0.098ms │
├────────╫──────────┼──────────┤
│ Day 11 ║  0.135ms │  0.092ms │
├────────╫──────────┼──────────┤
│ Day 12 ║  1.219ms │ 23.150ms │
├────────╫──────────┼──────────┤
│ Day 13 ║  0.089ms │  0.035ms │
├────────╫──────────┼──────────┤
│ Day 14 ║  0.064ms │ 10.489ms │
├────────╫──────────┼──────────┤
│ Day 15 ║  0.058ms │  0.267ms │
├────────╫──────────┼──────────┤
│ Day 16 ║  0.097ms │ 29.783ms │
├────────╫──────────┼──────────┤
│ Day 17 ║ 15.176ms │ 20.868ms │
├────────╫──────────┼──────────┤
│ Day 18 ║  0.018ms │  0.026ms │
├────────╫──────────┼──────────┤
│ Day 19 ║  0.063ms │  0.072ms │
└────────╨──────────┴──────────┘
```
