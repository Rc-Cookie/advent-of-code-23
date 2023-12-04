package de.rccookie.aoc.aoc23;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;

public class Solution4 extends Solution {

    @Override
    public Object task1() {
        return matchCounts().map(c -> c == 0 ? 0 : 1 << (c-1)).sum();
    }

    @Override
    public Object task2() {
        int[] matchCounts = matchCounts().toArray();
        int[] counts = new int[matchCounts.length];
        Arrays.fill(counts, 1);
        for(int i=0; i<counts.length-1; i++)
            for(int j=0; j<matchCounts[i]; j++)
                counts[i+j+1] += counts[i];
        return Mathf.sum(counts);
    }

    IntStream matchCounts() {
        return lines.mapToInt(line -> {
            String[] parts = line.split("\\s*[:|]\\s*");
            Set<String> winning = Arrays.stream(parts[1].split("\\s+")).collect(Collectors.toSet());
            return (int) Arrays.stream(parts[2].split("\\s+")).filter(winning::contains).count();
        });
    }
}
