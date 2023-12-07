package de.rccookie.aoc.aoc23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.rccookie.aoc.Solution;

public class Solution7 extends Solution {

    @Override
    public Object task1() {
        long sum = 0;
//        List<Hand> hands = new ArrayList<>();
        List<long[]> hands = new ArrayList<>();
        for(String line : linesArr) {
            String[] parts = line.split(" ");
//            hands.add(new Hand(parts[0], type(parts[0]), Integer.parseInt(parts[1])));
            hands.add(new long[] { value1(parts[0]), Long.parseLong(parts[1]) });
        }
//        hands.sort((a,b) -> {
//            if(a.type != b.type)
//                return -Long.compare(a.type, b.type);
//
//        });
        hands.sort(Comparator.comparingLong(h -> h[0]));
        for(int i=0; i<hands.size(); i++)
            sum += (i + 1) * hands.get(i)[1];

        return sum;
    }

    private static int value1(char card) {
        return switch(card) {
            case 'T' -> 8;
            case 'J' -> 9;
            case 'Q' -> 10;
            case 'K' -> 11;
            case 'A' -> 12;
            default -> card - '2';
        };
    }

    private static int type1(String hand) {
        int[] counts = new int[13];
        for(int i=0; i<hand.length(); i++)
            counts[value1(hand.charAt(i))]++;
        Arrays.sort(counts);

        if(counts[12] == 5) return 6;
        if(counts[12] == 4) return 5;
        if(counts[12] == 3)
            return counts[11] == 2 ? 4 : 3;
        if(counts[12] == 2)
            return counts[11] == 2 ? 2 : 1;
        return 0;
    }

    private static long tiebreaker1(String hand) {
        return (long) value1(hand.charAt(0)) << 32 |
               (long) value1(hand.charAt(1)) << 24 |
               (long) value1(hand.charAt(2)) << 16 |
               (long) value1(hand.charAt(3)) << 8 |
               value1(hand.charAt(4));
    }

    private static long value1(String hand) {
        return (long) type1(hand) << 36 | tiebreaker1(hand);
    }


    @Override
    public Object task2() {
        long[] hands = new long[linesArr.length];

        for(int i=0; i<hands.length; i++) {
            String[] parts = linesArr[i].split(" ");
            hands[i] = value2(parts[0]) << 16 | Long.parseLong(parts[1]);
            if(hands[i] >> 16 != value2(parts[0]) || (hands[i] & 0xFFFF) != Long.parseLong(parts[1]))
                throw new AssertionError();
        }
        Arrays.sort(hands);

        long sum = 0;
        for(int i=0; i<hands.length; i++)
            sum += (i+1) * (hands[i] & 0xFFFF);
        return sum;
    }

    private static int value2(char card) {
        return switch(card) {
            case 'T' -> 9;
            case 'J' -> 0;
            case 'Q' -> 10;
            case 'K' -> 11;
            case 'A' -> 12;
            default -> card - '1';
        };
    }

    private static int type2(String hand) {
        int[] counts = new int[13];
        for(int i=0; i<5; i++)
            counts[value2(hand.charAt(i))]++;

        int jCount = counts[0];
        counts[0] = 0;

        Arrays.sort(counts);

        if(counts[12] + jCount == 5) return 6;
        if(counts[12] + jCount == 4) return 5;
        if(counts[12] + jCount == 3)
            return counts[11] == 2 ? 4 : 3;
        if(counts[12] + jCount == 2)
            return counts[11] == 2 ? 2 : 1;
        return 0;
    }

    private static long tiebreaker2(String hand) {
        return (long) value2(hand.charAt(0)) << 32 |
               (long) value2(hand.charAt(1)) << 24 |
               (long) value2(hand.charAt(2)) << 16 |
               (long) value2(hand.charAt(3)) << 8 |
               value2(hand.charAt(4));
    }

    private static long value2(String hand) {
        return (long) type2(hand) << 36 | tiebreaker2(hand);
    }
}
