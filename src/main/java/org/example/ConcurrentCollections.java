package org.example;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class ConcurrentCollections {
    public static void main(String[] args){
//        skipList();
//        copyOnWrite();
        blockingQueue();
    }

    private static void blockingQueue() {
        try {
            var blockingQueue = new LinkedBlockingQueue<Integer>();
            blockingQueue.offer(39);
            blockingQueue.offer(3, 4, TimeUnit.SECONDS);
            System.out.println(blockingQueue.poll());
            System.out.println(blockingQueue.poll(10, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            // Handle interruption
        }
    }

    private static void copyOnWrite() {
        List<Integer> favNumbers =
                new CopyOnWriteArrayList<>(List.of(4,3,42));
        for(var n: favNumbers) {
            System.out.print(n + " ");
            favNumbers.add(9);
        }
        System.out.println();
        System.out.println("Size: " + favNumbers.size());

        Set<Character> favLetters =
                new CopyOnWriteArraySet<>(List.of('a','t'));
        for(char c: favLetters) {
            System.out.print(c+" ");
            favLetters.add('s');
        }
        System.out.println();
        System.out.println("Size: "+ favLetters.size());

        List<String> birds = new CopyOnWriteArrayList<>();
        birds.add("hawk");
        birds.add("hawk");
        birds.add("hawk");
        for (String bird : birds)
            birds.remove(bird);
        System.out.print(birds.size()); // 0
    }

    private static void skipList() {
        Set<String> gardenAnimals = new ConcurrentSkipListSet<>();
        gardenAnimals.add("rabbit");
        gardenAnimals.add("gopher");
        System.out.println(String.join(",", gardenAnimals)); // gopher,rabbit

        Map<String, String> rainForestAnimalDiet
                = new ConcurrentSkipListMap<>();
        rainForestAnimalDiet.put("koala", "bamboo");
        rainForestAnimalDiet.forEach((key, value) -> System.out.println(
                key + "-" + value)); // koala-bamboo
    }
}
