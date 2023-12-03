package de.rccookie.aoc.aoc23;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.rccookie.aoc.Solution;
import de.rccookie.math.IRect;
import de.rccookie.math.Mathf;
import de.rccookie.math.collision.BVH2;
import de.rccookie.math.collision.Data2D;
import de.rccookie.math.float2;

public class Solution3 extends Solution {

    private static final Pattern NUMBER = Pattern.compile("\\d+");

    @Override
    public Object task1() {
        BVH2<float2> symbols = symbols(c -> true);
        symbols.rebuild();
        return Mathf.sum(numbers(), n -> symbols.query(n).findAny().isPresent() ? n.data : 0);
    }

    @Override
    public Object task2() {
        BVH2<Data2D<IRect, Integer>> numbers = numbers();
        numbers.rebuild();
        return Mathf.sum(symbols(c -> c == '*'), g -> {
            List<Data2D<IRect, Integer>> nums = numbers.query(g).limit(3).toList();
            return nums.size() == 2 ? nums.get(0).data * nums.get(1).data : 0;
        });
    }

    private BVH2<Data2D<IRect, Integer>> numbers() {
        BVH2<Data2D<IRect, Integer>> numbers = new BVH2<>();
        for(int i=0; i<linesArr.length; i++) {
            Matcher m = NUMBER.matcher(linesArr[i]);
            while(m.find())
                numbers.insert(new Data2D<>(new IRect(m.start() - 1, i - 1, m.end() + 1, i + 2), Integer.parseInt(m.group())));
        }
        return numbers;
    }

    private BVH2<float2> symbols(Predicate<Character> filter) {
        BVH2<float2> symbols = new BVH2<>();
        for(int i=0; i<linesArr.length; i++)
            for(int j=0; j<linesArr[i].length(); j++)
                if(linesArr[i].charAt(j) != '.' && (linesArr[i].charAt(j) < '0' || linesArr[i].charAt(j) > '9') && filter.test(linesArr[i].charAt(j)))
                    symbols.insert(new float2(j,i).add(0.5f));
        return symbols;
    }
}
