package de.rccookie.aoc.aoc23;

import de.rccookie.aoc.Solution;

@SuppressWarnings("DuplicatedCode")
public class Solution4 extends Solution {

    @Override
    public Object task1() {
        int winStart = input.indexOf(':') + 1;
        int winEnd = input.indexOf('|', winStart + 3) - 1;
        int lineLen = input.indexOf('\n', winEnd + 4) + 1;
        int numWidth = 2;
        while(input.charAt(winStart + numWidth) == ' ') numWidth++;
        numWidth++;
        while(input.charAt(winStart + numWidth) != ' ') numWidth++;
        int numStart = winEnd + 2;
        int numCount = (lineLen - numStart) / numWidth;

        int sum = 0;
        for(int i = 0; i < input.length() / lineLen; i++) {
            int count = 0;
            for(int j=0; j<numCount; j++)
                if(input.indexOf(input.substring(i*lineLen + numStart + j * numWidth, i*lineLen + numStart + (j+1) * numWidth), i*lineLen + winStart, i*lineLen + winEnd) != -1)
                    count++;
            if(count != 0)
                sum += 1 << (count-1);
        }
        return sum;
    }

    @Override
    public Object task2() {

        int winStart = input.indexOf(':') + 1;
        int winEnd = input.indexOf('|', winStart + 3) - 1;
        int lineLen = input.indexOf('\n', winEnd + 4) + 1;
        int numWidth = 2;
        while(input.charAt(winStart + numWidth) == ' ') numWidth++;
        numWidth++;
        while(input.charAt(winStart + numWidth) != ' ') numWidth++;
        int numStart = winEnd + 2;
        int numCount = (lineLen - numStart) / numWidth;

        int sum = 0;
        int[] extraCount = new int[input.length() / lineLen];
        for(int i = 0; i < extraCount.length; i++) {
            int count = 0;
            for(int j=0; j<numCount; j++)
                if(input.indexOf(input.substring(i*lineLen + numStart + j * numWidth, i*lineLen + numStart + (j+1) * numWidth), i*lineLen + winStart, i*lineLen + winEnd) != -1)
                    count++;

            for(int j=0; j<count; j++)
                extraCount[i+j+1] += extraCount[i] + 1;
            sum += extraCount[i] + 1;
        }
        return sum;
    }
}
