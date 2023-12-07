# Advent of Code 2023

My solutions to (some of) the [Advent of Code](https://adventofcode.com) puzzles of 2023.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) package
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

## Performance

Some performance stats, which have been obtained using `--all --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 4.667ms

┌───────╥─────────┬─────────┐
│       ║  Task 1 │  Task 2 │
╞═══════╬═════════╪═════════╡
│ Day 1 ║ 0.415ms │ 0.756ms │
├───────╫─────────┼─────────┤
│ Day 2 ║ 0.849ms │ 0.755ms │
├───────╫─────────┼─────────┤
│ Day 3 ║ 0.199ms │ 0.173ms │
├───────╫─────────┼─────────┤
│ Day 4 ║ 0.228ms │ 0.202ms │
├───────╫─────────┼─────────┤
│ Day 5 ║ 0.201ms │ 0.319ms │
├───────╫─────────┼─────────┤
│ Day 6 ║ 0.005ms │ 0.003ms │
├───────╫─────────┼─────────┤
│ Day 7 ║ 0.321ms │ 0.240ms │
└───────╨─────────┴─────────┘
```
