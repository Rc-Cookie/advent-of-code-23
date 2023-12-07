# Advent of Code 2023

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2023.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) package
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

## Performance

Some performance stats, which have been obtained using `--all --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 20.956ms

┌───────╥─────────┬─────────┐
│       ║  Task 1 │  Task 2 │
╞═══════╬═════════╪═════════╡
│ Day 1 ║ 0.428ms │ 0.769ms │
├───────╫─────────┼─────────┤
│ Day 2 ║ 0.887ms │ 0.850ms │
├───────╫─────────┼─────────┤
│ Day 3 ║ 7.474ms │ 7.532ms │
├───────╫─────────┼─────────┤
│ Day 4 ║ 0.981ms │ 1.140ms │
├───────╫─────────┼─────────┤
│ Day 5 ║ 0.199ms │ 0.214ms │
├───────╫─────────┼─────────┤
│ Day 6 ║ 0.005ms │ 0.002ms │
├───────╫─────────┼─────────┤
│ Day 7 ║ 0.241ms │ 0.235ms │
└───────╨─────────┴─────────┘
```
