package de.rccookie.aoc.aoc23.clean;

import de.rccookie.aoc.Solution;

public class Solution13 extends Solution {

    @Override
    public Object task1() {
        return split("\n\n").mapToLong(p -> getReflection(p,0)).sum();
    }

    @Override
    public Object task2() {
        return split("\n\n").mapToLong(p -> getReflection(p,1)).sum();
    }

    private int getReflection(String pattern, int expectedWrongCount) {
        String[] lines = pattern.split("\n");

        splitLoop: for(int split = 1; split < lines[0].length(); split++) {
            int testCount = Math.min(split, lines[0].length() - split);
            int wrongCount = 0;
            for(String line : lines) {
                for(int i=0; i<testCount; i++) {
                    if(line.charAt(split + i) != line.charAt(split - i - 1)) {
                        if(++wrongCount > expectedWrongCount)
                            continue splitLoop;
                    }
                }
            }
            if(wrongCount == expectedWrongCount)
                return split;
        }
        splitLoop: for(int split = 1; split < lines.length; split++) {
            int testCount = Math.min(split, lines.length- split);
            int wrongCount = 0;
            for(int j=0; j<lines[0].length(); j++) {
                for(int i=0; i<testCount; i++) {
                    if(lines[split + i].charAt(j) != lines[split - i - 1].charAt(j)) {
                        if(++wrongCount > expectedWrongCount)
                            continue splitLoop;
                    }
                }
            }
            if(wrongCount == expectedWrongCount)
                return split * 100;
        }
        throw new AssertionError();
    }
}
