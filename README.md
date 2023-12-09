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

Some performance stats of the optimized solutions, which have been obtained using `--all --repeat 100` on my AMD Ryzen 9 5950x:

```
Duration (average of 100 runs): 6.288ms

┌───────╥─────────┬─────────┐
│       ║  Task 1 │  Task 2 │
╞═══════╬═════════╪═════════╡
│ Day 1 ║ 0.617ms │ 0.811ms │
├───────╫─────────┼─────────┤
│ Day 2 ║ 0.990ms │ 0.870ms │
├───────╫─────────┼─────────┤
│ Day 3 ║ 0.209ms │ 0.194ms │
├───────╫─────────┼─────────┤
│ Day 4 ║ 0.249ms │ 0.154ms │
├───────╫─────────┼─────────┤
│ Day 5 ║ 0.247ms │ 0.206ms │
├───────╫─────────┼─────────┤
│ Day 6 ║ 0.005ms │ 0.003ms │
├───────╫─────────┼─────────┤
│ Day 7 ║ 0.352ms │ 0.245ms │
├───────╫─────────┼─────────┤
│ Day 8 ║ 0.115ms │ 0.326ms │
├───────╫─────────┼─────────┤
│ Day 9 ║ 0.345ms │ 0.350ms │
└───────╨─────────┴─────────┘
```
