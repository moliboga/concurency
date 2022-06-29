package org.example;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SubmittingTaskCollection {

    private static void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService service = null;
        try{
            service = Executors.newSingleThreadExecutor();
            System.out.println("begin");
            Callable<Integer> task = () -> {
                int x = 0;
                for (int i = 0; i < 10; i++){
                    Thread.sleep(100);
                    x = i;
                }
                return x;
            };
            List<Future<Integer>> list = service.invokeAll(List.of(task, task, task));
            for (Future<Integer> future : list) {
                System.out.println(future.get());
            }
            System.out.println("end");
        }
        finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        invokeAll();
        invokeAny();
    }

    private static void invokeAny() throws ExecutionException, InterruptedException {
        ExecutorService service = null;
        try{
            service = Executors.newSingleThreadExecutor();
            System.out.println("begin");
            AtomicReference<Integer> x = new AtomicReference<>(0);
            Callable<String> task = () -> {
                x.set(x.get() + 1);
                return "result" + x.get().toString();
            };
            String data = service.invokeAny(List.of(task, task, task));
            System.out.println(data);
            System.out.println("end");
        }
        finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}
