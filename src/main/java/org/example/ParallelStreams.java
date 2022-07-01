package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreams {
    public static void main(String[] args){
//        streamCreating();
//        parallelReduction();
//        combiningWithReduce();
//        combiningWithCollect();
        avoidStatefulOperations();
    }

    private static void avoidStatefulOperations() {
        var list = addValues(IntStream.range(1, 11).parallel());
        var list2 = addValues2(IntStream.range(1, 11).parallel());
        System.out.println(list);
        System.out.println(list2);
    }

    public static List<Integer> addValues(IntStream source) {
        var data = Collections.synchronizedList(new ArrayList<Integer>());
        source.filter(s -> s % 2 == 0)
                .forEach(data::add); // STATEFUL: DON'T DO THIS!
        return data;
    }

    public static List<Integer> addValues2(IntStream source) {
        return source.filter(s -> s % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    private static void combiningWithCollect() {
        Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
        SortedSet<String> set = stream.collect(ConcurrentSkipListSet::new,
                Set::add,
                Set::addAll);
        System.out.println(set); // [f, l, o, w]

    }

    private static void combiningWithReduce() {
        System.out.println(List.of('w', 'o', 'l', 'f')
                .parallelStream()
                .reduce("",
                        (s1,c) -> s1 + c,
                        (s2,s3) -> s2 + s3)); // wolf

        System.out.println(List.of("w","o","l","f")
                .parallelStream()
                .reduce("X", String::concat)); // XwXoXlXf
    }

    private static void parallelReduction() {
        System.out.print(List.of(1,2,3,4,5,6)
                .parallelStream()
                .findAny().get());
    }

    private static void streamCreating() {
        long start = System.currentTimeMillis();
        List.of(1,2,3,4,5)
                .parallelStream()
                .map(ParallelStreams::doWork)
                .forEachOrdered(s -> System.out.print(s + " "));
        System.out.println();
        var timeTaken = (System.currentTimeMillis()-start)/1000.0;
        System.out.println("Time: " + timeTaken + " seconds");
    }

    private static int doWork(int input) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
        return input;
    }
}
