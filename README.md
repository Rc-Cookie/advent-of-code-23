# Advent of Code 2023

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2023.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) package
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

## Performance

Some performance stats, which have been obtained using `--all --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 6.852ms

┌───────╥─────────┬─────────┐
│       ║  Task 1 │  Task 2 │
╞═══════╬═════════╪═════════╡
│ Day 1 ║ 0.423ms │ 0.812ms │
├───────╫─────────┼─────────┤
│ Day 2 ║ 0.887ms │ 0.888ms │
├───────╫─────────┼─────────┤
│ Day 3 ║ 0.240ms │ 0.174ms │
├───────╫─────────┼─────────┤
│ Day 4 ║ 1.372ms │ 0.929ms │
├───────╫─────────┼─────────┤
│ Day 5 ║ 0.345ms │ 0.242ms │
├───────╫─────────┼─────────┤
│ Day 6 ║ 0.006ms │ 0.003ms │
├───────╫─────────┼─────────┤
│ Day 7 ║ 0.392ms │ 0.363ms │
└───────╨─────────┴─────────┘
```
