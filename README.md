# Advent of Code 2023

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2023.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) package
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

## Performance

Some performance stats, which have been obtained using `--all --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 23.191598ms

┌───────╥─────────┬─────────┐
│       ║  Task 1 │  Task 2 │
╞═══════╬═════════╪═════════╡
│ Day 1 ║ 0.523ms │ 1.235ms │
├───────╫─────────┼─────────┤
│ Day 2 ║ 1.395ms │ 0.602ms │
├───────╫─────────┼─────────┤
│ Day 3 ║ 7.899ms │ 8.311ms │
├───────╫─────────┼─────────┤
│ Day 4 ║ 1.263ms │ 0.984ms │
├───────╫─────────┼─────────┤
│ Day 5 ║ 0.194ms │ 0.216ms │
├───────╫─────────┼─────────┤
│ Day 6 ║ 0.007ms │ 0.003ms │
├───────╫─────────┼─────────┤
│ Day 7 ║ 0.262ms │ 0.298ms │
└───────╨─────────┴─────────┘
```
