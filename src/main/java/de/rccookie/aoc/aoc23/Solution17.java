package de.rccookie.aoc.aoc23;

import java.util.Arrays;

import com.diogonunes.jcolor.Attribute;
import de.rccookie.aoc.Solution;
import de.rccookie.graph.DistancePath;
import de.rccookie.graph.DoublyLinkedHashGraph;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.math.Mathf;
import de.rccookie.math.constInt3;
import de.rccookie.math.int3;
import de.rccookie.util.Console;
import de.rccookie.util.T2;

import static de.rccookie.util.Tuples.t;

public class Solution17 extends Solution {

    private static final byte RIGHT = 0;
    private static final byte DOWN = 1;
    private static final byte LEFT = 2;
    private static final byte UP = 3;

    private static final int[] DIRS_X = { 1, 0, -1, 0 };
    private static final int[] DIRS_Y = { 0, 1, 0, -1 };

    enum State {
        /* 0 */ ENTERED_HORIZONTAL(t(DOWN, 3), t(UP, 5), t(DOWN, 1), t(UP, 1)),
        /* 1 */ ENTERED_VERTICAL(t(RIGHT, 2), t(LEFT, 4), t(RIGHT, 0), t(LEFT, 0)),
        /* 2 */ SECOND_STRAIGHT_R(t(RIGHT, 6), t(RIGHT, 0)),
        /* 3 */ SECOND_STRAIGHT_D(t(DOWN, 7), t(DOWN, 1)),
        /* 4 */ SECOND_STRAIGHT_L(t(LEFT, 8), t(LEFT, 0)),
        /* 5 */ SECOND_STRAIGHT_U(t(UP, 9), t(UP, 1)),
        /* 6 */ THIRD_STRAIGHT_R(t(RIGHT, 0)),
        /* 7 */ THIRD_STRAIGHT_D(t(DOWN, 1)),
        /* 8 */ THIRD_STRAIGHT_L(t(LEFT, 0)),
        /* 9 */ THIRD_STRAIGHT_U(t(UP, 1));

        static final State[] STATES = values();
        private final T2<Byte, Integer>[] next;

        @SafeVarargs
        State(T2<Byte, Integer>... next) {
            this.next = next;
        }
    }

    @Override
    public Object task1() {
        return findPath(1, 3);
    }

    @Override
    public Object task2() {
        return findPath(4, 10);
    }

    private int findPath(int minMove, int maxMove) {

        Graph<constInt3, Integer> graph = new DoublyLinkedHashGraph<>();

        for(int i=0; i<charTable.length; i++) {
            for(int j=0; j<charTable[i].length; j++) {
                for(State state : State.STATES) {
                    constInt3 node = new int3(j, i, state.ordinal());
                    for(T2<Byte, Integer> next : state.next) {
                        byte dir = next.a;
                        int nextState = next.b;
                        constInt3 nextNode = new constInt3(j + DIRS_X[dir], i + DIRS_Y[dir], nextState);
                        if(nextNode.x() >= 0 && nextNode.y() >= 0 && nextNode.x() < charTable[0].length && nextNode.y() < charTable.length)
                            graph.connect(node, nextNode, charTable[nextNode.y()][nextNode.x()] - '0');
                    }
                }
            }
        }

        Console.log(graph);

        for(constInt3 n : graph)
            for(int e : graph.inverse().adj(n).values())
                if(e != charTable[n.y()][n.x()] - '0')
                    Console.map(n, e+" != "+(charTable[n.y()][n.x()] - '0'));
        Console.log("First validation done.");

        constInt3 start = new constInt3(0, 0, -1);
        graph.connect(start, new constInt3(1, 0, State.SECOND_STRAIGHT_L.ordinal()), charTable[0][1] - '0');
        graph.connect(start, new constInt3(0, 1, State.SECOND_STRAIGHT_D.ordinal()), charTable[1][0] - '0');
        graph.connect(start, new constInt3(1, 0, State.ENTERED_HORIZONTAL.ordinal()), charTable[0][1] - '0');
        graph.connect(start, new constInt3(0, 1, State.ENTERED_VERTICAL.ordinal()), charTable[1][0] - '0');

        Console.log(graph);

        for(constInt3 n : graph)
            for(int e : graph.inverse().adj(n).values())
                if(e != charTable[n.y()][n.x()] - '0')
                    Console.map(n, e+" != "+(charTable[n.y()][n.x()] - '0'));
        Console.log("Second validation done.");

        constInt3 end = new constInt3(charTable[0].length - 1, charTable.length - 1, -1);
        for(State state : State.STATES)
            graph.set(end.withZ(state.ordinal()), end);

        for(constInt3 n : graph)
            for(int e : graph.inverse().adj(n).values())
                if(e != charTable[n.y()][n.x()] - '0')
                    Console.map(n, e+" != "+(charTable[n.y()][n.x()] - '0'));

        Console.log(graph);

        DistancePath<constInt3, Integer> path = Graphs.shortestPath(
                graph,
                start,
                end,
                Integer::doubleValue,
                (a,b) -> Math.abs(b.x() - a.x()) + Math.abs(b.y() - a.y())
        );
        Console.log(path);
        String[][] output = new String[charTable.length][charTable[0].length];
        for(int i=0; i<output.length; i++) {
            int _i = i;
            Arrays.setAll(output[i], j -> charTable[_i][j] + "");
        }

        path.forEach((a,b,d) -> {
            int3 diff = b.subed(a);
            if(diff.x() < 0)
                output[a.y()][a.x()] = Console.colored("<", Attribute.BOLD(), Attribute.RED_TEXT());
            else if(diff.x() > 0)
                output[a.y()][a.x()] = Console.colored(">", Attribute.BOLD(), Attribute.RED_TEXT());
            else if(diff.y() < 0)
                output[a.y()][a.x()] = Console.colored("^", Attribute.BOLD(), Attribute.RED_TEXT());
            else output[a.y()][a.x()] = Console.colored("v", Attribute.BOLD(), Attribute.RED_TEXT());
        });
        for(int i=0; i<charTable.length; i++) {
            System.out.println(String.join("", output[i]));
        }
        return Mathf.round(path.distance());
    }
}
