package de.rccookie.aoc.aoc23.clean;

import java.util.Arrays;

import de.rccookie.aoc.Solution;

public class Solution6 extends Solution {

    @Override
    public Object task1() {

        long[] times = Arrays.stream(linesArr[0].split("\\s+")).skip(1).mapToLong(Long::parseLong).toArray();
        long[] distances = Arrays.stream(linesArr[1].split("\\s+")).skip(1).mapToLong(Long::parseLong).toArray();

        long options = 1;
        for(int i=0; i<times.length; i++)
            options *= winCount(times[i], distances[i]);
        return options;
    }

    @Override
    public Object task2() {
        return winCount(
                Long.parseLong(linesArr[0].replaceAll("\\D", "")),
                Long.parseLong(linesArr[1].replaceAll("\\D", ""))
        );
    }

    private static long winCount(long time, long distance) {
        // Binary search for lowest beating time
        long low = 1, high = (time + 1) / 2, i;
        while(low != high) {
            i = (low + high) / 2;
            if(i * (time - i) > distance) {
                if((i-1) * (time - (i-1)) <= distance)
                    // Function is symmetric around i = time/2, so we can calculate the number of
                    // wins from knowing the lowest beating time
                    return time - 2*i + 1;
                high = i;
            }
            else low = i+1;
        }
        return time - 2*low + 1;
    }
}
