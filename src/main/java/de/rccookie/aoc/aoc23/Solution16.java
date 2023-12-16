package de.rccookie.aoc.aoc23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.rccookie.aoc.Solution;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.graph.HashGraph;
import de.rccookie.math.Mathf;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

public class Solution16 extends Solution {

    private static final byte RIGHT = 0;
    private static final byte DOWN = 1;
    private static final byte LEFT = 2;
    private static final byte UP = 3;

    private static final constInt2[] DIRS = { int2.X, int2.Y, int2.X.negated(), int2.Y.negated() };
    private static final char[] SPLITTER_FOR_DIR = { '|', '-', '|', '-' };
    private final byte[][] NEXT_DIRS = new byte[128][];
    {
        NEXT_DIRS['.'] = new byte[] { RIGHT,  DOWN,  LEFT,    UP };
        NEXT_DIRS['|'] = new byte[] {    UP,  DOWN,    UP,    UP };
        NEXT_DIRS['-'] = new byte[] { RIGHT, RIGHT,  LEFT, RIGHT };
        NEXT_DIRS['/'] = new byte[] {    UP,  LEFT,  DOWN, RIGHT };
        NEXT_DIRS['\\']= new byte[] {  DOWN, RIGHT,    UP,  LEFT };
    }

    @Override
    public Object task1() {
        return countEnergized(int2.zero, RIGHT);
    }

    @Override
    public Object task2() {

        createGraph();
        Graphs.expandToTransitiveClosure(graph);
        cache = new HashMap<>();

        int max = 0;
        for(int i=0; i<charTable.length; i++) {
            max = Mathf.max(
                    max,
                    countEnergizedCached(new int2(0,i), RIGHT),
                    countEnergizedCached(new int2(charTable.length - 1, i), LEFT)
            );
        }
        for(int i=0; i<charTable[0].length; i++) {
            max = Mathf.max(
                    max,
                    countEnergizedCached(new int2(i,0), DOWN),
                    countEnergizedCached(new int2(i, charTable[0].length - 1), UP)
            );
        }
        return max;
    }

    private int countEnergized(constInt2 start, int dir) {
        byte[][] energized = new byte[charTable.length][charTable[0].length];
        followLight(start, dir, energized);
        int count = 0;
        for(int i=0; i<energized.length; i++)
            for(int j=0; j<energized[i].length; j++)
                if(energized[i][j] != 0)
                    count++;
        return count;
    }

    private void followLight(constInt2 start, int dir, byte[][] energized) {
        int2 pos = start.clone();
        while(pos.geq(constInt2.zero) && pos.y() < energized.length && pos.x() < energized[0].length && (energized[pos.y()][pos.x()] & 1 << dir) == 0) {
            energized[pos.y()][pos.x()] |= (byte) (1 << dir);

            char c = charTable[pos.y()][pos.x()];
            if(c == SPLITTER_FOR_DIR[dir]) {
                dir = NEXT_DIRS[c][dir];
                followLight(pos, (dir+2) & 3, energized);
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }
    }

    private Graph<constInt2, Object> graph;
    private Map<constInt2, byte[][]> cache;

    private void createGraph() {
        graph = new HashGraph<>();
        for(int i=0; i<charTable.length; i++) for(int j=0; j<charTable[i].length; j++)
            if(charTable[i][j] == '-' || charTable[i][j] == '|')
                createGraph(new int2(j,i));
    }

    private void createGraph(constInt2 start) {
        if(!graph.add(start.toConst())) return;

        int dir = NEXT_DIRS[charTable[start.y()][start.x()]][0];
        int2 pos = start.clone().add(DIRS[dir]);

        while(pos.geq(constInt2.zero) && pos.y() < charTable.length && pos.x() < charTable[0].length) {

            char c = charTable[pos.y()][pos.x()];
            if(c == '-' || c == '|') {
                createGraph(pos);
                graph.connect(start.toConst(), pos.toConst());
                break;
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }

        dir = (NEXT_DIRS[charTable[start.y()][start.x()]][0] + 2) & 3;
        pos.set(start).add(DIRS[dir]);

        while(pos.geq(constInt2.zero) && pos.y() < charTable.length && pos.x() < charTable[0].length) {

            char c = charTable[pos.y()][pos.x()];
            if(c == '-' || c == '|') {
                createGraph(pos);
                graph.connect(start.toConst(), pos.toConst());
                break;
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }
    }

    private int countEnergizedCached(constInt2 start, int dir) {
        Set<constInt2> additional = new HashSet<>();
        while(start.geq(int2.zero) && start.y() < charTable.length && start.x() < charTable[0].length) {
            char c = charTable[start.y()][start.x()];
            if(c == '-' || c == '|') {
                byte[][] res = followLightCached(start);

                int2 tmp = int2.zero();
                int count = 0;
                for(int i=0; i<res.length; i++)
                    for(int j=0; j<res[i].length; j++)
                        if(res[i][j] != 0 || additional.contains(tmp.set(j,i)))
                            count++;
                return count;
            }
            additional.add(start);
            dir = NEXT_DIRS[c][dir];
            start = start.added(DIRS[dir]);
        }
        return additional.size();
    }

    private byte[][] followLightCached(constInt2 start) {

        byte[][] result = cache.get(start);
        if(result != null)
            return result;
        result = new byte[charTable.length][charTable[0].length];
        followLightCached(start, result, new HashSet<>());
        cache.put(start.toConst(), result);
        return result;
    }

    private void followLightCached(constInt2 start, byte[][] result, Set<constInt2> dejavu) {
        if(!dejavu.add(start.toConst())) return;

        int dir = NEXT_DIRS[charTable[start.y()][start.x()]][0];
        int2 pos = start.clone();
        result[pos.y()][pos.x()] |= (byte) (1 << dir);
        pos.add(DIRS[dir]);

        while(pos.geq(constInt2.zero) && pos.y() < result.length && pos.x() < result[0].length) {
            result[pos.y()][pos.x()] |= (byte) (1 << dir);

            char c = charTable[pos.y()][pos.x()];
            if(c == '-' || c == '|') {
                if(graph.connected(pos, start))
                    followLightCached(pos, result, dejavu);
                else {
                    byte[][] res = followLightCached(pos);
                    for(int i=0; i<res.length; i++) for(int j=0; j<res[i].length; j++)
                        result[i][j] |= res[i][j];
                }
                break;
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }

        dir = (NEXT_DIRS[charTable[start.y()][start.x()]][0] + 2) & 3;
        pos = start.clone();
        result[pos.y()][pos.x()] |= (byte) (1 << dir);
        pos.add(DIRS[dir]);

        while(pos.geq(constInt2.zero) && pos.y() < result.length && pos.x() < result[0].length) {
            result[pos.y()][pos.x()] |= (byte) (1 << dir);

            char c = charTable[pos.y()][pos.x()];
            if(c == '-' || c == '|') {
                if(graph.connected(pos, start))
                    followLightCached(pos, result, dejavu);
                else {
                    byte[][] res = followLightCached(pos);
                    for(int i=0; i<res.length; i++) for(int j=0; j<res[i].length; j++)
                        result[i][j] |= res[i][j];
                }
                break;
            }
            else dir = NEXT_DIRS[c][dir];
            pos.add(DIRS[dir]);
        }
    }
}
