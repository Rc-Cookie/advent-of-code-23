package de.rccookie.aoc.aoc23.clean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;
import org.jetbrains.annotations.NotNull;

public class Solution5 extends Solution {

    @Override
    public Object task1() {
        RangeMap[] maps = RangeMap.parseAll(input);
        return Arrays.stream(linesArr[0].split("\\s+"))
                .skip(1)
                .mapToLong(Long::parseLong)
                .map(x -> {
                    for(RangeMap map : maps)
                        x = map.map(x);
                    return x;
                }).min().getAsLong();
    }

    @Override
    public Object task2() {
        // Store the ranges sorted by starting point; we need them sorted in order to join overlapping ranges
        Queue<Range> valueRanges = new PriorityQueue<>();
        // Store the seed ranges
        String[] parts = linesArr[0].split("\\s+");
        for(int i = 1; i < parts.length; i += 2)
            valueRanges.add(Range.withSize(Long.parseLong(parts[i]), Long.parseLong(parts[i + 1])));

        // Map ranges layer by layer (first map all over first map, then all over the second, etc.). This way allows to
        // optimize overlapping ranges away
        for(RangeMap map : RangeMap.parseAll(input)) {

            // Join overlapping ranges to prevent unnecessary duplicate calculation
            List<Range> combined = new ArrayList<>();
            Range current = valueRanges.remove();
            while(!valueRanges.isEmpty()) {
                Range range = valueRanges.remove();
                if(current.overlaps(range))
                    current = current.join(range);
                else {
                    combined.add(current);
                    current = range;
                }
            }
            combined.add(current);

            // Calculate mapping for all combined ranges and add them back to the ranges queue
            valueRanges.addAll(combined.stream().flatMap(map::mapAll).toList());
        }

        // Find the lowest range by starting value
        return Mathf.minL(valueRanges, Range::min);
    }

    private record RangeMap(MappedRange[] ranges) {

        long map(long x) {
            // Binary search for lowest range that contains it
            int low = 0, high = ranges.length;
            while(low != high) {
                int i = (low + high) / 2;
                if(ranges[i].src.min <= x) {
                    if(ranges[i].src.contains(x))
                        return ranges[i].map(x);
                    low = i + 1;
                }
                else high = i;
            }
            return x;
        }

        Stream<Range> mapAll(Range range) {

            if(range.isEmpty()) return Stream.empty();

            // Binary search for lowest range that the range starts in / after
            int low = 0, high = ranges.length;
            int i = -1;
            while(low != high) {
                i = (low + high) / 2;
                if(ranges[i].src.min <= range.min) {
                    if(i == ranges.length-1 || ranges[i+1].src.min > range.min) break;
                    low = i + 1;
                }
                else high = i;
            }

            List<Range> mappedRanges = new ArrayList<>();
            while(true) {
                if(ranges[i].src.contains(range.min)) {
                    // This means that the range is starting within an actively defined range (which maps to a specific other range)
                    if(ranges[i].src.contains(range)) {
                        // The range fully lays within this range, all ranges mapped
                        mappedRanges.add(ranges[i].map(range));
                        return mappedRanges.stream();
                    }
                    Range overlap = range.overlap(ranges[i].src);
                    mappedRanges.add(ranges[i].map(overlap));
                    range = new Range(overlap.max, range.max);
                }
                else {
                    // This means that the range does not start within an actively defined range and is mapped to itself
                    long rangeEnd = i == ranges.length - 1 ? Long.MAX_VALUE : ranges[i+1].src.min;
                    if(rangeEnd == ranges[i].src.max) {
                        // The last range and the next range are touching each other, thus the "passive" range in between is empty
                        i++;
                        continue;
                    }

                    if(range.max <= rangeEnd) {
                        // The range fully lays within this "passive" range, all ranges mapped
                        mappedRanges.add(range);
                        return mappedRanges.stream();
                    }
                    mappedRanges.add(new Range(range.min, rangeEnd));
                    range = new Range(rangeEnd, range.max);
                    i++;
                }
            }
        }

        static RangeMap[] parseAll(String str) {
            return Arrays.stream(str.split("\n\n"))
                    .skip(1)
                    .map(lines -> new RangeMap(lines.lines().skip(1)
                        .map(line -> Arrays.stream(line.split("\\s+")).mapToLong(Long::parseLong).toArray())
                        .map(nums -> new MappedRange(Range.withSize(nums[1], nums[2]), Range.withSize(nums[0], nums[2])))
                        .sorted()
                        .toArray(MappedRange[]::new)
                    )).toArray(RangeMap[]::new);
        }

        record MappedRange(Range src, Range dst) implements Comparable<MappedRange> {
            @Override
            public int compareTo(@NotNull MappedRange o) {
                return src.compareTo(o.src);
            }

            public long map(long x) {
                return x + (dst.min - src.min);
            }

            public Range map(Range r) {
                return new Range(map(r.min), map(r.max));
            }
        }
    }

    record Range(long min, long max) implements Comparable<Range> {
        public long size() {
            return max - min;
        }

        @Override
        public int compareTo(@NotNull Range o) {
            return Long.compare(min, o.min);
        }

        public boolean isEmpty() {
            return min == max;
        }

        public boolean contains(long x) {
            return x >= min && x < max;
        }

        public boolean contains(Range range) {
            return contains(range.min) && contains(range.max - 1);
        }

        public boolean overlaps(Range range) {
            return max >= range.min && min < range.max;
        }

        public Range overlap(Range other) {
            return new Range(Math.max(min, other.min), Math.min(max, other.max));
        }

        public Range join(Range other) {
            return new Range(Math.min(min, other.min), Math.max(max, other.max));
        }

        public static Range withSize(long low, long size) {
            return new Range(low, low + size);
        }
    }
}
