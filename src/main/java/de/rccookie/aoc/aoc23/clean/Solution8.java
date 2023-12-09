package de.rccookie.aoc.aoc23.clean;

import java.util.Map;
import java.util.stream.Collectors;

import de.rccookie.aoc.Solution;

@SuppressWarnings("DuplicatedCode")
public class Solution8 extends Solution {

    @Override
    public Object task1() {
        int[] dirs = parseDirections();
        Map<String, String[]> network = parseNetwork();

        String pos = "AAA";
        int count = 0;
        do {
            pos = network.get(pos)[dirs[count % dirs.length]];
            count++;
        } while(!pos.equals("ZZZ"));
        return count;
    }

    @Override
    public Object task2() {
        int[] dirs = parseDirections();
        Map<String, String[]> network = parseNetwork();

        return network.keySet()
                .stream()
                .filter(pos -> pos.endsWith("A"))
                .mapToLong(pos -> {
                    int count = 0;
                    do {
                        pos = network.get(pos)[dirs[count % dirs.length]];
                        count++;
                    } while(!pos.endsWith("Z"));
                    return count / dirs.length;
                })
                .reduce(dirs.length, (a,b) -> a*b);
    }

    private int[] parseDirections() {
        return linesArr[0].chars().map(c -> c == 'L' ? 0 : 1).toArray();
    }

    private Map<String, String[]> parseNetwork() {
        return lines.skip(2)
                .map(l -> l.split("\\s*=\\s*\\(|\\s*,\\s*|\\)"))
                .collect(Collectors.toMap(ps -> ps[0], ps -> new String[] { ps[1], ps[2] }));
    }
}
