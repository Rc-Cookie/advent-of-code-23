package de.rccookie.aoc.aoc23;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.rccookie.aoc.Solution;
import de.rccookie.math.constInt3;
import de.rccookie.math.int3;

public class Solution2 extends Solution {

    private static final List<String> NAMES = List.of("red", "green", "blue");
    private static final constInt3 MAX_COUNTS = new int3(12, 13, 14);

    @Override
    public Object task1() {
        return sum(line -> {
            Game game = parse(line);
            for(int3 reveal : game.revealed)
                if(!reveal.leq(MAX_COUNTS)) return 0;
            return game.id;
        });
    }

    @Override
    public Object task2() {
        return sum(line -> {
            Game game = parse(line);
            int3 max = int3.zero();
            for(int3 reveal : game.revealed)
                max = int3.max(max, reveal);
            return max.product();
        });
    }

    Game parse(String line) {
        String[] parts = line.split("[:;]\\s*");
        int id = Integer.parseInt(parts[0].substring(5));
        Set<int3> revealed = new HashSet<>();
        for(int i=1; i<parts.length; i++) {
            int3 counts = int3.zero();
            for(String count : parts[i].split(",\\s*")) {
                String[] ps = count.split(" ");
                counts.setComponent(NAMES.indexOf(ps[1]), Integer.parseInt(ps[0]));
            }
            revealed.add(counts);
        }
        return new Game(id, revealed);
    }

    record Game(int id, Set<int3> revealed) { }
}
