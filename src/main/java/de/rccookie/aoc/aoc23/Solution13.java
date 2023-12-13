package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;

public class Solution13 extends Solution {

    @Override
    public Object task1() {
        return getReflections(0);
    }

    @Override
    public Object task2() {
        return getReflections(1);
    }

    private int getReflections(int expectedWrongCount) {
        int len = input.length();
        int sum = 0;
        for(int pos = 0; pos < len; ) {
            int width = input.indexOf('\n', pos) - pos;
            int height = 1;
            while(pos + (width+1) * height < input.length() && input.charAt(pos + (width+1) * height) != '\n')
                height++;
            sum += getReflection(pos, expectedWrongCount, width, height, len);
            pos += (width+1) * height + 1;
        }
        return sum;
    }

    private int getReflection(int start, int expectedWrongCount, int width, int height, int len) {

        splitLoop: for(int split = 1; split < width; split++) {
            int testCount = Math.min(split, width - split);
            int wrongCount = 0;
            for(int pos = start; pos < len && input.charAt(pos) != '\n'; pos += width + 1)
                for(int i=0; i<testCount; i++)
                    if(input.charAt(pos + split + i) != input.charAt(pos + split - i - 1) && ++wrongCount > expectedWrongCount)
                        continue splitLoop;
            if(wrongCount == expectedWrongCount)
                return split;
        }

        splitLoop: for(int split = 1; split < height; split++) {
            int testCount = Math.min(split, height - split);
            int wrongCount = 0;
            for(int j=0; j<width; j++)
                for(int i=0; i<testCount; i++)
                    if(input.charAt(start + (split + i) * (width+1) + j) != input.charAt(start + (split - i - 1) * (width+1) + j) && ++wrongCount > expectedWrongCount)
                        continue splitLoop;
            if(wrongCount == expectedWrongCount)
                return split * 100;
        }
        throw new AssertionError();
    }
}
