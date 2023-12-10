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
Duration (average of 100 runs): 3.953ms

┌────────╥─────────┬─────────┐
│        ║  Task 1 │  Task 2 │
╞════════╬═════════╪═════════╡
│  Day 1 ║ 0.351ms │ 0.713ms │
├────────╫─────────┼─────────┤
│  Day 2 ║ 0.456ms │ 0.641ms │
├────────╫─────────┼─────────┤
│  Day 3 ║ 0.164ms │ 0.114ms │
├────────╫─────────┼─────────┤
│  Day 4 ║ 0.178ms │ 0.201ms │
├────────╫─────────┼─────────┤
│  Day 5 ║ 0.056ms │ 0.121ms │
├────────╫─────────┼─────────┤
│  Day 6 ║ 0.004ms │ 0.002ms │
├────────╫─────────┼─────────┤
│  Day 7 ║ 0.192ms │ 0.166ms │
├────────╫─────────┼─────────┤
│  Day 8 ║ 0.090ms │ 0.182ms │
├────────╫─────────┼─────────┤
│  Day 9 ║ 0.084ms │ 0.063ms │
├────────╫─────────┼─────────┤
│ Day 10 ║ 0.076ms │ 0.102ms │
└────────╨─────────┴─────────┘
```
