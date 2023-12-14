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
Duration (average of 100 runs): 39.886ms

┌────────╥─────────┬──────────┐
│        ║  Task 1 │   Task 2 │
╞════════╬═════════╪══════════╡
│  Day 1 ║ 0.314ms │  0.857ms │
├────────╫─────────┼──────────┤
│  Day 2 ║ 0.774ms │  0.772ms │
├────────╫─────────┼──────────┤
│  Day 3 ║ 0.264ms │  0.091ms │
├────────╫─────────┼──────────┤
│  Day 4 ║ 0.190ms │  0.175ms │
├────────╫─────────┼──────────┤
│  Day 5 ║ 0.057ms │  0.156ms │
├────────╫─────────┼──────────┤
│  Day 6 ║ 0.004ms │  0.002ms │
├────────╫─────────┼──────────┤
│  Day 7 ║ 0.149ms │  0.162ms │
├────────╫─────────┼──────────┤
│  Day 8 ║ 0.071ms │  0.182ms │
├────────╫─────────┼──────────┤
│  Day 9 ║ 0.088ms │  0.075ms │
├────────╫─────────┼──────────┤
│ Day 10 ║ 0.082ms │  0.096ms │
├────────╫─────────┼──────────┤
│ Day 11 ║ 0.124ms │  0.071ms │
├────────╫─────────┼──────────┤
│ Day 12 ║ 1.231ms │ 21.595ms │
├────────╫─────────┼──────────┤
│ Day 13 ║ 0.051ms │  0.035ms │
├────────╫─────────┼──────────┤
│ Day 14 ║ 0.032ms │ 12.188ms │
└────────╨─────────┴──────────┘
```
