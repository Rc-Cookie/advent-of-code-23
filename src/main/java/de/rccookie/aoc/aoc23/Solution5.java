package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import de.rccookie.math.Mathf;

public class Solution5 extends FastSolution {

    @Override
    public void load() {
        //noinspection ResultOfMethodCallIgnored
        Mathf.max();
    }

    @Override
    public Object task1() {
        long[] values = Arrays.stream(linesArr[0].split("\\s+")).skip(1).mapToLong(Long::parseLong).toArray();

        for(RangeMap map : RangeMap.parseAll(input))
            for(int i=0; i<values.length; i++)
                values[i] = map.map(values[i]);

        return Mathf.min(values);
    }

    @Override
    public Object task2() {
        // Store the ranges sorted by starting point; we need them sorted in order to join overlapping ranges
        Queue<long[]> valueRanges = new PriorityQueue<>(Comparator.comparingLong(r -> r[0]));
        // Store the seed ranges
        String[] parts = linesArr[0].split("\\s+");
        for(int i = 1; i < parts.length; i += 2) {
            long start = Long.parseLong(parts[i]), len = Long.parseLong(parts[i + 1]);
            valueRanges.add(new long[]{start, start + len});
        }

        // Temporary swap storage used to store the joined ranges
        List<long[]> combined = new ArrayList<>();

        // Map ranges layer by layer (first map all over first map, then all over the second, etc.). This way allows to
        // optimize overlapping ranges away
        for(RangeMap map : RangeMap.parseAll(input)) {

            // Join overlapping ranges to prevent unnecessary duplicate calculation
            combined.clear();
            long[] current = valueRanges.remove();
            while(!valueRanges.isEmpty()) {
                long[] range = valueRanges.remove();
                if(range[0] <= current[1])
                    current[1] = Math.max(current[1], range[1]);
                else {
                    combined.add(current);
                    current = range;
                }
            }
            combined.add(current);

            // Calculate mapping for all combined ranges and add them back to the ranges queue
            for(long[] range : combined)
                valueRanges.addAll(map.mapRange(range[0], range[1]));
        }

        // Find the lowest range by starting value
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

            // Binary search for lowest range that the range starts in / after
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

            List<long[]> mappedRanges = new ArrayList<>();
            while(true) {
                if(start < rangeEnds[i]) {
                    // This means that the range is starting within an actively defined range (which maps to a specific other range)
                    long mappedStart = targetStarts[i] + (start - rangeStarts[i]);

                    if(end <= rangeEnds[i]) {
                        // The range fully lays within this range, all ranges mapped
                        mappedRanges.add(new long[]{ mappedStart, mappedStart + (end - start) });
                        return mappedRanges;
                    }
                    long mappedLen = rangeEnds[i] - start;
                    mappedRanges.add(new long[]{ mappedStart, mappedStart + mappedLen });
                    start += mappedLen;
                }
                else {
                    // This means that the range does not start within an actively defined range and is mapped to itself
                    long rangeEnd = i == rangeStarts.length - 1 ? Long.MAX_VALUE : rangeStarts[i+1];
                    if(rangeEnd == rangeEnds[i]) {
                        // The last range and the next range are touching each other, thus the "passive" range in between is empty
                        i++;
                        continue;
                    }

                    if(end <= rangeEnd) {
                        // The range fully lays within this "passive" range, all ranges mapped
                        mappedRanges.add(new long[] { start, end });
                        return mappedRanges;
                    }
                    mappedRanges.add(new long[] { start, rangeEnd });
                    start += rangeEnd - start;
                    i++;
                }
            }
        }

        static RangeMap[] parseAll(String str) {
            // There are exactly 7 maps, as they have specific names
            RangeMap[] maps = new RangeMap[7];

            // Don't use String.split() (very slow) or a reader (still slower) to avoid creating
            // unnecessary copies of the string (and because compiling a regex is slow)

            int pos = str.indexOf('\n', str.indexOf('\n') + 1) + 1;
            for(int k=0; k<maps.length; k++) {
                pos = str.indexOf('\n', pos) + 1;

                Queue<long[]> rangeMaps = new PriorityQueue<>(Comparator.comparingLong(r -> r[1]));
                // Add a lowest range so there is no need to always test for the lower bounds and there is no special case for it
                rangeMaps.add(new long[] { -1, -1, 0 });
                int eol = str.indexOf('\n', pos);
                while(eol - pos > 2) {
                    rangeMaps.add(new long[] {
                            Long.parseLong(str, pos, pos = str.indexOf(' ', pos), 10),
                            Long.parseLong(str, ++pos, pos = str.indexOf(' ', pos), 10),
                            Long.parseLong(str, ++pos, pos = eol, 10)
                    });
                    eol = str.indexOf('\n', ++pos);
                }
                pos = eol + 1;

                long[] rangeStarts = new long[rangeMaps.size()];
                long[] rangeEnds = new long[rangeMaps.size()];
                long[] targetStarts = new long[rangeMaps.size()];
                for(int i=0; !rangeMaps.isEmpty(); i++) {
                    long[] range = rangeMaps.remove();
                    rangeStarts[i] = range[1];
                    rangeEnds[i] = rangeStarts[i] + range[2];
                    targetStarts[i] = range[0];
                }
                maps[k] = new RangeMap(rangeStarts, rangeEnds, targetStarts);
            }
            return maps;
        }
    }
}
