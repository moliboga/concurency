package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledTasks {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        schedule();
        scheduleAtFixedRate();
    }

    private static void scheduleAtFixedRate() throws InterruptedException {
        ScheduledExecutorService service
                = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);
        Runnable task = () -> System.out.println("Task" + counter.incrementAndGet());
        service.scheduleAtFixedRate(task, 1000, 500, TimeUnit.MILLISECONDS);
        Thread.sleep(3000);
        service.shutdown();
    }

    private static void schedule() throws ExecutionException, InterruptedException {
        ScheduledExecutorService service
                = Executors.newSingleThreadScheduledExecutor();
        Runnable task1 = () -> System.out.println("Hello Zoo");
        Callable<String> task2 = () -> "Monkey";
        ScheduledFuture<?> r1 = service.schedule(task1, 3, TimeUnit.SECONDS);
        ScheduledFuture<?> r2 = service.schedule(task2, 2, TimeUnit.SECONDS);
        System.out.println(r2.get());
        service.shutdown();
    }
}
