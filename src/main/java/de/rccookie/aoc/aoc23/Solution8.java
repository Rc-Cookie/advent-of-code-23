package de.rccookie.aoc.aoc23;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.Solution;

public class Solution8 extends Solution {

    @Override
    public Object task1() {
        Map<String, String[]> network = new HashMap<>();
        input.lines().skip(2).forEach(line -> {
            String[] parts = line.split("\\s*=\\s*\\(|,\\s*|\\)");
            network.put(parts[0], new String[] { parts[1], parts[2] });
        });
        boolean[] leftRight = new boolean[linesArr[0].length()];
        for(int i=0; i<linesArr[0].length(); i++)
            leftRight[i] = linesArr[0].charAt(i) == 'L';

        String cur = "AAA";
        int count = 0;
        do {
            int left = leftRight[count % leftRight.length] ? 1 : 0;
            cur = network.get(cur)[left];
            count++;
        } while(!"ZZZ".equals(cur));

        return count;
    }

    @Override
    public Object task2() {

        Map<String, String[]> network = new HashMap<>();
        input.lines().skip(2).forEach(line -> {
            String[] parts = line.split("\\s*=\\s*\\(|,\\s*|\\)");
            network.put(parts[0], new String[] { parts[1], parts[2] });
        });
        boolean[] leftRight = new boolean[linesArr[0].length()];
        for(int i=0; i<linesArr[0].length(); i++)
            leftRight[i] = linesArr[0].charAt(i) == 'L';

        String[] cur = network.keySet().stream().filter(s -> s.endsWith("A")).toArray(String[]::new);
        long count = 0;
        do {
            int left = leftRight[(int) (count % leftRight.length)] ? 1 : 0;
            for(int i=0; i<cur.length; i++)
                cur[i] = network.get(cur[i])[left];
            count++;
        } while(Arrays.stream(cur).anyMatch(s -> !s.endsWith("Z")));

        return count;
    }
}
