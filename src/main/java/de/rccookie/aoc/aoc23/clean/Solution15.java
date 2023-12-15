package de.rccookie.aoc.aoc23.clean;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import de.rccookie.aoc.Solution;

public class Solution15 extends Solution {
    @Override
    public Object task1() {
        return split(",").mapToLong(this::hash).sum();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object task2() {
        Map<String, Integer>[] boxes = new Map[256];
        Arrays.setAll(boxes, i -> new LinkedHashMap<>());
        for(String instr : input.replace("\n", "").split(",")) {
            if(instr.endsWith("-"))
                boxes[hash(instr.substring(0, instr.length() - 1))].remove(instr.substring(0, instr.length() - 1));
            else {
                String[] parts = instr.split("[=-]");
                boxes[hash(parts[0])].put(parts[0], Integer.parseInt(parts[1]));
            }
        }
        long sum = 0;
        for(int i=0; i<boxes.length; i++) {
            int j=0;
            for(int f : boxes[i].values())
                sum += (i+1L) * (++j) * f;
        }
        return sum;
    }

    private int hash(String str) {
        int hash = 0;
        for(int i=0; i<str.length(); i++)
            if(input.charAt(i) != '\n')
                hash = ((hash + input.charAt(i)) * 17) & 0xFF;
        return hash;
    }
}
