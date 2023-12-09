package de.rccookie.aoc.aoc23.clean;

import java.util.Arrays;

import de.rccookie.aoc.Solution;

@SuppressWarnings("DuplicatedCode")
public class Solution9 extends Solution {

    @Override
    public Object task1() {
        return lines.map(l -> Arrays.stream(l.split("\\s+")).mapToInt(Integer::parseInt).toArray())
                .mapToInt(nums -> {
                    int next = nums[nums.length - 1];
                    int count = nums.length - 1;
                    boolean allEqual;
                    do {
                        allEqual = true;
                        for(int i=0; i<count; i++) {
                            nums[i] = nums[i+1] - nums[i];
                            allEqual &= i == 0 || nums[i-1] == nums[i];
                        }
                        next += nums[count - 1];
                        count--;

                    } while(!allEqual);
                    return next;
                }).sum();
    }

    @Override
    public Object task2() {
        return lines.map(l -> Arrays.stream(l.split("\\s+")).mapToInt(Integer::parseInt).toArray())
                .mapToInt(nums -> {
                    int next = nums[0];
                    int count = nums.length - 1;
                    int sign = -1;
                    boolean allEqual;
                    do {
                        allEqual = true;
                        for(int i=0; i<count; i++) {
                            nums[i] = nums[i+1] - nums[i];
                            allEqual &= i == 0 || nums[i-1] == nums[i];
                        }
                        next += sign * nums[0];
                        count--;
                        sign = -sign;

                    } while(!allEqual);
                    return next;
                }).sum();
    }
}
