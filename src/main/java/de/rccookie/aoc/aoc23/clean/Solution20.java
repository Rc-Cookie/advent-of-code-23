package de.rccookie.aoc.aoc23.clean;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import de.rccookie.aoc.Solution;
import de.rccookie.graph.DoublyLinkedGraph;
import de.rccookie.graph.Graphs;
import de.rccookie.graph.MapGraph;
import de.rccookie.graph.ReadableGraph;
import de.rccookie.util.Console;
import de.rccookie.util.T2;
import org.jetbrains.annotations.NotNull;

import static de.rccookie.util.Tuples.t;

public class Solution20 extends Solution {

    @Override
    public Object task1() {
        MapGraph<String, Module, Boolean> network = parseNetwork();

        long lowCount = 0;
        long highCount = 0;
        for(int i=0; i<1000; i++) {
            Queue<T2<String, Boolean>> signalQueue = new ArrayDeque<>();
            signalQueue.add(t("broadcaster", false));
            while(!signalQueue.isEmpty()) {
                T2<String, Boolean> signal = signalQueue.remove();
                if(signal.b) highCount++;
                else lowCount++;
                Boolean output = network.get(signal.a).onSignal(signal.a, signal.b, network);
                if(output != null) {
                    for(String next : network.adj(signal.a)) {
                        network.connect(signal.a, next, output);
                        signalQueue.add(t(next, output));
                    }
                }
            }
        }
        return lowCount * highCount;
    }

    @Override
    public Object task2() {
        MapGraph<String, Module, Boolean> network = parseNetwork();
        String conj = network.inverse().adj("rx").iterator().next();
        Set<String> required = network.inverse().adj(conj).keySet().stream().map(prev -> network.inverse().adj(prev).iterator().next()).collect(Collectors.toSet());

        List<Integer> counts = new ArrayList<>();
        for(String start : network.adj("broadcaster")) {
            int count = 0;
            buttonLoop: while(true) {
                count++;
                Queue<T2<String, Boolean>> signalQueue = new ArrayDeque<>();
                signalQueue.add(t(start, false));
                while(!signalQueue.isEmpty()) {
                    T2<String, Boolean> signal = signalQueue.remove();
                    Boolean output = network.get(signal.a).onSignal(signal.a, signal.b, network);
                    if(output != null) {
                        if(!output && required.contains(signal.a))
                            break buttonLoop;
                        for(String next : network.adj(signal.a)) {
                            network.connect(signal.a, next, output);
                            signalQueue.add(t(next, output));
                        }
                    }
                }
            }
            Console.map(start, count);
            counts.add(count);
        }

        return counts.stream().mapToLong(x->x).reduce(1, (a,b) -> a*b);
    }

    @NotNull
    private MapGraph<String, Module, Boolean> parseNetwork() {
        MapGraph<String, Module, Boolean> network = MapGraph.of(new DoublyLinkedGraph<>(Graphs.usingMap(LinkedHashMap::new), Graphs.usingMap(LinkedHashMap::new)), new HashMap<>());
        for(String line : lines) {
            String[] parts = line.split("\\s*->\\s*|,\\s*");
            String name;
            Module module;
            if(parts[0].equals("broadcaster")) {
                name = "broadcaster";
                module = new BroadcastModule();
            }
            else {
                name = parts[0].substring(1);
                module = parts[0].charAt(0) == '%' ? new FlipFlopModule() : new ConjunctionModule();
            }
            network.put(name, module);
            network.connectToAll(name, Arrays.asList(parts).subList(1, parts.length), false);
        }
        network.put("rx", new BroadcastModule());
        return network;
    }


    private interface Module {
        Boolean onSignal(String name, boolean signal, ReadableGraph<String, Boolean> network);
    }

    private static class BroadcastModule implements Module {
        @Override
        public Boolean onSignal(String name, boolean signal, ReadableGraph<String, Boolean> network) {
            return signal;
        }
    }

    private static class FlipFlopModule implements Module {
        boolean state = false;
        @Override
        public Boolean onSignal(String name, boolean signal, ReadableGraph<String, Boolean> network) {
            if(signal) return null;
            return state ^= true;
        }
    }

    private static class ConjunctionModule implements Module {
        @Override
        public Boolean onSignal(String name, boolean signal, ReadableGraph<String, Boolean> network) {
            return !network.inverse().adj(name).values().stream().allMatch(in -> in);
        }
    }
}
