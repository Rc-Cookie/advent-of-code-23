package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;

public class Solution5 extends Solution {

    @Override
    public Object task1() {
        long[] values = Arrays.stream(linesArr[0].split("\\s+")).skip(1).mapToLong(Long::parseLong).toArray();
        RangeMap[] maps = split("\n\n").skip(1).map(RangeMap::parse).toArray(RangeMap[]::new);

        for(RangeMap map : maps)
            for(int i=0; i<values.length; i++)
                values[i] = map.map(values[i]);

        return Mathf.min(values);
    }

    @Override
    public Object task2() {
        List<long[]> valueRanges = new ArrayList<>();
        String[] parts = linesArr[0].split("\\s+");
        for(int i=1; i<parts.length; i+=2)
            valueRanges.add(new long[] { Long.parseLong(parts[i]), Long.parseLong(parts[i]) + Long.parseLong(parts[i+1]) });

        RangeMap[] maps = split("\n\n").skip(1).map(RangeMap::parse).toArray(RangeMap[]::new);

        for(RangeMap map : maps)
            valueRanges = valueRanges.stream().flatMap(r -> map.mapRange(r[0], r[1]).stream()).toList();

        return Mathf.minL(valueRanges, r -> r[0]);
    }

    private record RangeMap(long[] rangeStarts, long[] rangeEnds, long[] targetStarts) {

        long map(long x) {
            // Binary search for lowest range that contains it
            int low = 0, high = rangeStarts.length;
            while(low != high) {
                int i = (low + high) / 2;
                if(rangeStarts[i] <= x) {
                    if(rangeEnds[i] > x)
                        return targetStarts[i] + (x - rangeStarts[i]);
                    low = i + 1;
                }
                else high = i;
            }
            return x;
        }

        List<long[]> mapRange(long start, long end) {

            if(start == end) return List.of();

            int low = 0, high = rangeStarts.length;
            int i = -1;
            while(low != high) {
                i = (low + high) / 2;
                if(rangeStarts[i] <= start) {
                    if(i == rangeStarts.length-1 || rangeStarts[i+1] > start) break;
                    low = i + 1;
                }
                else high = i;
            }

//            int i = rangeStarts.length - 1;
//            while(rangeStarts[i] > start) i--;

            List<long[]> mappedRanges = new ArrayList<>();
            while(true) {
                if(start < rangeEnds[i]) {
                    long mappedStart = targetStarts[i] + (start - rangeStarts[i]);

                    if(end <= rangeEnds[i]) {
                        mappedRanges.add(new long[]{ mappedStart, mappedStart + (end - start) });
                        return mappedRanges;
                    }
                    long mappedLen = rangeEnds[i] - start;
                    mappedRanges.add(new long[]{ mappedStart, mappedStart + mappedLen });
                    start += mappedLen;
                }
                else {
                    long rangeEnd = i == rangeStarts.length - 1 ? Long.MAX_VALUE : rangeStarts[i+1];
                    if(rangeEnd == rangeEnds[i]) {
                        i++;
                        continue;
                    }

                    if(end <= rangeEnd) {
                        mappedRanges.add(new long[] { start, end });
                        return mappedRanges;
                    }
                    mappedRanges.add(new long[] { start, rangeEnd });
                    start += rangeEnd - start;
                    i++;
                }
            }
        }

        static RangeMap parse(String str) {
            List<long[]> rangeMaps = new ArrayList<>();
            str.lines().skip(1).forEach(line -> {
                String[] parts = line.split("\\s+");
                rangeMaps.add(new long[]{
                        Long.parseLong(parts[0]),
                        Long.parseLong(parts[1]),
                        Long.parseLong(parts[2])
                });
            });
            rangeMaps.add(new long[] { -1, -1, 0 }); // Always have a low enough range

            rangeMaps.sort(Comparator.comparingLong(a -> a[1]));

            long[] rangeStarts = new long[rangeMaps.size()];
            long[] rangeEnds = new long[rangeMaps.size()];
            long[] targetStarts = new long[rangeMaps.size()];
            for(int i = 0; i < rangeMaps.size(); i++) {
                rangeStarts[i] = rangeMaps.get(i)[1];
                rangeEnds[i] = rangeStarts[i] + rangeMaps.get(i)[2];
                targetStarts[i] = rangeMaps.get(i)[0];
            }
            return new RangeMap(rangeStarts, rangeEnds, targetStarts);
        }
    }
}
