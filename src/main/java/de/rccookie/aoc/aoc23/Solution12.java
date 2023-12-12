package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rccookie.aoc.Solution;
import de.rccookie.util.T3;
import de.rccookie.util.Tuples;

public class Solution12 extends Solution {

    private final Map<T3<String, List<Integer>, Integer>, Long> cache = new HashMap<>();

    @Override
    public Object task1() {
        return sum(line -> options(line.substring(0, line.indexOf(' ')), Arrays.stream(line.split(",|\\s")).skip(1).map(Integer::parseInt).toList(), -1, 0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object task2() {
        cache.clear();
        return lines.mapToLong(line -> {
            List<Integer> nums = Arrays.stream(line.split(",|\\s")).skip(1).map(Integer::parseInt).toList();
            List<Integer> unfolded = new ArrayList<>();
            for(int i=0; i<5; i++)
                unfolded.addAll(nums);
            List<Integer>[] subLists = new List[unfolded.size() + 1];
            subLists[0] = unfolded;
            for(int i=1; i<subLists.length; i++)
                subLists[i] = unfolded.subList(i, unfolded.size());
            int[] minSizes = new int[subLists.length];
            Arrays.setAll(minSizes, i -> subLists[i].stream().mapToInt(x->x).sum() + subLists[i].size() - 1);

            String str = (line.substring(0, line.indexOf(' '))+"?").repeat(5).substring(0, line.indexOf(' ')*5+4);
            String[] substrings = new String[str.length() + 1];
            for(int i=0; i<substrings.length; i++)
                substrings[i] = str.substring(i);
            return options(substrings, subLists, minSizes, -1, 0, 0);
        }).sum();
    }

    private long options(String records, List<Integer> remaining, int active, int pos) {
        if(pos == records.length())
            return remaining.isEmpty() && active <= 0 ? 1 : 0;
        char c = records.charAt(pos);
        if(c == '.')
            return active <= 0 ? options(records, remaining, -1, pos + 1) : 0;
        if(active > 0)
            return options(records, remaining, active - 1, pos + 1);
        if(active == 0 || remaining.isEmpty())
            return c == '#' ? 0 : options(records, remaining, -1, pos + 1);
        long options = c == '?' ? options(records, remaining, -1, pos + 1) : 0;
        return options + options(records, remaining.subList(1, remaining.size()), remaining.get(0) - 1, pos + 1);
    }

    private long options(String[] records, List<Integer>[] remaining, int[] minSizes, int active, int pos, int remainingPos) {
        if(records[pos].isEmpty())
            return remaining[remainingPos].isEmpty() && active <= 0 ? 1 : 0;
        if(records[pos].length() < minSizes[remainingPos])
            return 0;
        T3<String, List<Integer>, Integer> t = Tuples.t(records[pos], remaining[remainingPos], active);
        Long cached = cache.get(t);
        if(cached != null)
            return cached;

        long res = calc(records, remaining, minSizes, active, pos, remainingPos);
        cache.put(t, res);
        return res;
    }

    private long calc(String[] records, List<Integer>[] remaining, int[] minSizes, int active, int pos, int remainingPos) {
        char c = records[pos].charAt(0);
        if(c == '.')
            return active <= 0 ? options(records, remaining, minSizes, -1, pos + 1, remainingPos) : 0;
        if(active > 0)
            return options(records, remaining, minSizes, active - 1, pos + 1, remainingPos);
        if(remaining[remainingPos].isEmpty()) {
            for(int i=0; i<records[pos].length(); i++)
                if(records[pos].charAt(i) == '#') return 0;
            return 1;
        }
        if(active == 0)
            return c == '#' ? 0 : options(records, remaining, minSizes, -1, pos + 1, remainingPos);

        long options = c == '?' ? options(records, remaining, minSizes, -1, pos + 1, remainingPos) : 0;

        int expected = remaining[remainingPos].get(0);
        if(expected > records[pos].length())
            return options;
        for(int i=0; i<expected; i++)
            if(records[pos].charAt(i) == '.')
                return options;
        if(records[pos].length() == expected)
            return remaining[remainingPos].size() == 1 ? 1 : 0;
        if(records[pos].charAt(expected) == '#')
            return options;
        return options + options(records, remaining, minSizes, -1, pos + expected + 1, remainingPos + 1);
    }
}
