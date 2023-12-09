package de.rccookie.aoc.aoc23.clean;

import java.util.Arrays;
import java.util.Comparator;

import de.rccookie.aoc.Solution;
import de.rccookie.util.IntWrapper;
import org.jetbrains.annotations.NotNull;

public class Solution7 extends Solution {

    @Override
    public Object task1() {
        IntWrapper index = new IntWrapper(1);
        return lines.map(Hand::parse)
                .sorted()
                .mapToLong(Hand::bet)
                .map(b -> b * index.value++)
                .sum();
    }

    @Override
    public Object task2() {
        IntWrapper index = new IntWrapper(1);
        return lines.map(l -> l.replace('J', '*'))
                .map(Hand::parse)
                .sorted()
                .mapToLong(Hand::bet)
                .map(b -> b * index.value++)
                .sum();
    }

    record Hand(char[] cards, int bet) implements Comparable<Hand> {

        @Override
        public int compareTo(@NotNull Hand o) {
            return Comparator.comparing(Hand::type).thenComparingLong(Hand::highCardValue).compare(this, o);
        }

        public Type type() {
            int[] counts = new int[14];
            for(int i=0; i<cards.length; i++)
                counts[value(cards[i])]++;

            int jokerCount = counts[0];
            counts[0] = 0;

            Arrays.sort(counts);

            return switch(counts[13] + jokerCount) {
                case 5 -> Type.FIVE_OF_A_KIND;
                case 4 -> Type.FOUR_OF_A_KIND;
                case 3 -> counts[12] == 2 ? Type.FULL_HOUSE : Type.THREE_OF_A_KIND;
                case 2 -> counts[12] == 2 ? Type.TWO_PAIRS : Type.PAIR;
                default -> Type.HIGH_CARD;
            };
        }

        public long highCardValue() {
            return (long) value(cards[0]) << 32 |
                   (long) value(cards[1]) << 24 |
                   (long) value(cards[2]) << 16 |
                   (long) value(cards[3]) << 8 |
                   value(cards[4]);
        }

        private static int value(char card) {
            return switch(card) {
                case '*' -> 0;
                case 'T' -> 9;
                case 'J' -> 10;
                case 'Q' -> 11;
                case 'K' -> 12;
                case 'A' -> 13;
                default -> card - '1';
            };
        }

        public static Hand parse(String line) {
            return new Hand(line.substring(0,5).toCharArray(), Integer.parseInt(line.substring(5).trim()));
        }

        enum Type {
            HIGH_CARD,
            PAIR,
            TWO_PAIRS,
            THREE_OF_A_KIND,
            FULL_HOUSE,
            FOUR_OF_A_KIND,
            FIVE_OF_A_KIND
        }
    }
}
