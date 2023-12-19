package de.rccookie.aoc.aoc23;

@SuppressWarnings("DuplicatedCode")
public class Solution9 extends FastSolution {

    @Override
    public Object task1() {
        int[] nums = new int[22];
        int pos = 0;
        int sum = 0;
        int len = input.length();
        while(pos != len) {
            int count = 0;
            nums[0] = 0;
            int sign = 1;
            while(true) {
                char c = input.charAt(pos++);
                if(c == '\n') {
                    count++;
                    break;
                }
                if(c == ' ') {
                    count++;
                    nums[count] = 0;
                    sign = 1;
                    continue;
                }
                if(c == '-')
                    sign = -1;
                else nums[count] = 10 * nums[count] + sign * (c - '0');
            }

            int next = nums[count - 1];
            count--;

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

            sum += next;
        }
        return sum;
    }

    @Override
    public Object task2() {
        int[] nums = new int[22];
        int pos = 0;
        int sum = 0;
        int len = input.length();
        while(pos != len) {
            int count = 0;
            nums[0] = 0;
            int sign = 1;
            while(true) {
                char c = input.charAt(pos++);
                if(c == '\n') {
                    count++;
                    break;
                }
                if(c == ' ') {
                    count++;
                    nums[count] = 0;
                    sign = 1;
                    continue;
                }
                if(c == '-')
                    sign = -1;
                else nums[count] = 10 * nums[count] + sign * (c - '0');
            }

            int next = nums[0];
            count--;

            sign = -1;
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

            sum += next;
        }
        return sum;
    }
}
