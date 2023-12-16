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
Duration (average of 100 runs): 64.728ms

┌────────╥─────────┬──────────┐
│        ║  Task 1 │   Task 2 │
╞════════╬═════════╪══════════╡
│  Day 1 ║ 0.272ms │  0.608ms │
├────────╫─────────┼──────────┤
│  Day 2 ║ 0.856ms │  0.625ms │
├────────╫─────────┼──────────┤
│  Day 3 ║ 0.145ms │  0.060ms │
├────────╫─────────┼──────────┤
│  Day 4 ║ 0.175ms │  0.143ms │
├────────╫─────────┼──────────┤
│  Day 5 ║ 0.050ms │  0.216ms │
├────────╫─────────┼──────────┤
│  Day 6 ║ 0.005ms │  0.002ms │
├────────╫─────────┼──────────┤
│  Day 7 ║ 0.149ms │  0.141ms │
├────────╫─────────┼──────────┤
│  Day 8 ║ 0.078ms │  0.185ms │
├────────╫─────────┼──────────┤
│  Day 9 ║ 0.078ms │  0.076ms │
├────────╫─────────┼──────────┤
│ Day 10 ║ 0.071ms │  0.099ms │
├────────╫─────────┼──────────┤
│ Day 11 ║ 0.202ms │  0.072ms │
├────────╫─────────┼──────────┤
│ Day 12 ║ 1.249ms │ 21.506ms │
├────────╫─────────┼──────────┤
│ Day 13 ║ 0.020ms │  0.035ms │
├────────╫─────────┼──────────┤
│ Day 14 ║ 0.067ms │  9.939ms │
├────────╫─────────┼──────────┤
│ Day 15 ║ 0.054ms │  0.210ms │
├────────╫─────────┼──────────┤
│ Day 16 ║ 0.108ms │ 27.231ms │
└────────╨─────────┴──────────┘
```
