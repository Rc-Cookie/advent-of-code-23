package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rccookie.aoc.Solution;
import de.rccookie.math.Mathf;
import de.rccookie.util.Console;
import de.rccookie.util.T2;
import de.rccookie.util.Tuples;

public class Solution12 extends Solution {

    private final Map<T2<String, List<Integer>>, Long> cache = new HashMap<>();


    @Override
    public Object task1() {
        return sum(line -> options(line, 1)); // options(line.substring(0, line.indexOf(' ')), Arrays.stream(line.split(",|\\s")).skip(1).map(Integer::parseInt).toList(), -1, 0));
    }

    @Override
    public Object task2() {
        cache.clear();
        Console.log(Mathf.max(lines, l -> l.replaceAll("[^,]", "").length() + 1));
        return lines.mapToLong(line -> {
//            List<Integer> nums = Arrays.stream(line.split(",|\\s")).skip(1).map(Integer::parseInt).toList();
//            List<Integer> unfolded = new ArrayList<>();
//            for(int i=0; i<5; i++)
//                unfolded.addAll(nums);
//            List<Integer>[] subLists = new List[unfolded.size() + 1];
//            subLists[0] = unfolded;
//            for(int i=1; i<subLists.length; i++)
//                subLists[i] = unfolded.subList(i, unfolded.size());
//            int[] minSizes = new int[subLists.length];
//            Arrays.setAll(minSizes, i -> subLists[i].stream().mapToInt(x->x).sum() + subLists[i].size() - 1);
//            minSizes[minSizes.length-1] = 0;
//
//            String str = (line.substring(0, line.indexOf(' '))+"?").repeat(5).substring(0, line.indexOf(' ')*5+4);
//            String[] substrings = new String[str.length() + 1];
//            for(int i=0; i<substrings.length; i++)
//                substrings[i] = str.substring(i);
//            long res = options(substrings, subLists, minSizes, -1, 0, 0);
//            Console.map(line, res);
//            return res;
            return options(line, 5);
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

    @SuppressWarnings("unchecked")
    private long options(String line, int repeatCount) {

        List<Integer> nums = Arrays.stream(line.split(",|\\s")).skip(1).map(Integer::parseInt).toList();
        List<Integer> unfolded = new ArrayList<>();
        for(int i=0; i<repeatCount; i++)
            unfolded.addAll(nums);
        List<Integer>[] subLists = new List[unfolded.size() + 1];
        subLists[0] = unfolded;
        for(int i=1; i<subLists.length; i++)
            subLists[i] = unfolded.subList(i, unfolded.size());
        int[] minSizes = new int[subLists.length];
        Arrays.setAll(minSizes, i -> subLists[i].stream().mapToInt(x->x).sum() + subLists[i].size() - 1);
        minSizes[minSizes.length - 1] = 0;

        String records = line.substring(0, line.indexOf(' '));
        String str = (records+"?").repeat(repeatCount).substring(0, (records.length() + 1) * repeatCount - 1);
        String[] substrings = new String[str.length() + 1];
        for(int i=0; i<substrings.length; i++)
            substrings[i] = str.substring(i);

        return options(substrings, subLists, minSizes, 0, 0);
    }

    private long options(String[] records, List<Integer>[] remaining, int[] minSizes, int pos, int remainingPos) {
        if(records[pos].isEmpty())
            return remaining[remainingPos].isEmpty() ? 1 : 0;
        if(records[pos].length() < minSizes[remainingPos])
            return 0;
        T2<String, List<Integer>> t = Tuples.t(records[pos], remaining[remainingPos]);
        Long cached = cache.get(t);
        if(cached != null)
            return cached;

        long res = calc(records, remaining, minSizes, pos, remainingPos);
        cache.put(t, res);
        return res;
    }

    static final byte[] MAPPING = new byte[Mathf.max('.', '?', '#') + 1];
    static {
        MAPPING['.'] = 0;
        MAPPING['?'] = 1;
        MAPPING['#'] = 2;
    }

    private long hash(String record, int start, int end, List<Integer> remaining) {
        long h = 0;
        for(int i=start; i<end; i++)
            h = 3 * h + MAPPING[record.charAt(i)];
        h = h << 32;
        for(int i=0; i<remaining.size(); i++)
            h |= (long) remaining.get(i) << 5*i;
        return h;
    }

    private long calc(String[] records, List<Integer>[] remaining, int[] minSizes, int pos, int remainingPos) {

        while(records[pos].charAt(0) == '.')
            if(records[++pos].isEmpty())
                return remaining[remainingPos].isEmpty() ? 1 : 0;

        char c = records[pos].charAt(0);
        if(remaining[remainingPos].isEmpty()) {
            for(int i=0; i<records[pos].length(); i++)
                if(records[pos].charAt(i) == '#') return 0;
            return 1;
        }

        if(c == '?') {
            int count = 1;
            while (count < records[pos].length() && records[pos].charAt(count) == '?')
                count++;
            if(count == records[pos].length() || (records[pos].indexOf('#', count) == -1 && records[pos].indexOf('?', count) == -1)) {
                return possibleCombinations(count, minSizes, remainingPos, remaining[remainingPos].size());
            }
            if(records[pos].charAt(count - 1) == '.') {
                long options = 0;
                for (int i = 0; i <= remaining[remainingPos].size(); i++) {
                    long combinations = possibleCombinations(count, minSizes, remainingPos, i);
                    if(combinations != 0)
                        options += options(records, remaining, minSizes, pos + count + 1, remainingPos + i);
                }
                return options;
            }
        }

        long options = c == '?' ? options(records, remaining, minSizes, pos + 1, remainingPos) : 0;

        int expected = remaining[remainingPos].get(0);
        if(expected > records[pos].length())
            return options;
        for (int i = 0; i < expected; i++)
            if(records[pos].charAt(i) == '.')
                return options;
        if(records[pos].length() == expected)
            return remaining[remainingPos].size() == 1 ? 1 : 0;
        if(records[pos].charAt(expected) == '#')
            return options;
        return options + options(records, remaining, minSizes, pos + expected + 1, remainingPos + 1);
    }

    private static long possibleCombinations(int range, int[] minSizes, int remainingPos, int count) {
        int minSize;
        if(remainingPos + count == minSizes.length - 1)
            minSize = minSizes[remainingPos];
        else if(count == 0)
            minSize = 0;
        else minSize = minSizes[remainingPos] - minSizes[remainingPos + count] - 1;

        int free = range - minSize;
        return bin(count + free, free);
    }

    private static long bin(int n, int k) {
        k = Math.min(k, n-k);
        long res = 1;
        for(int i=1; i<=k; i++)
            res = res * (n+1-i) / i;
        return res;
    }
}
