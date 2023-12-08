# Advent of Code 2023

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2023.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) package
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

## Performance

Some performance stats, which have been obtained using `--all --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 5.683ms

┌───────╥─────────┬─────────┐
│       ║  Task 1 │  Task 2 │
╞═══════╬═════════╪═════════╡
│ Day 1 ║ 0.539ms │ 0.768ms │
├───────╫─────────┼─────────┤
│ Day 2 ║ 1.002ms │ 0.883ms │
├───────╫─────────┼─────────┤
│ Day 3 ║ 0.188ms │ 0.176ms │
├───────╫─────────┼─────────┤
│ Day 4 ║ 0.245ms │ 0.206ms │
├───────╫─────────┼─────────┤
│ Day 5 ║ 0.299ms │ 0.239ms │
├───────╫─────────┼─────────┤
│ Day 6 ║ 0.005ms │ 0.003ms │
├───────╫─────────┼─────────┤
│ Day 7 ║ 0.303ms │ 0.278ms │
├───────╫─────────┼─────────┤
│ Day 8 ║ 0.187ms │ 0.364ms │
└───────╨─────────┴─────────┘
```
