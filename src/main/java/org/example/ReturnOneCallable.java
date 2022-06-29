package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ReturnOneCallable {

    public static void main(String[] args) throws Exception {
        ExecutorService service = null;
        AtomicReference<Integer> currentX = new AtomicReference<>(0);
        try {
            service = Executors.newSingleThreadExecutor();
            Future<AtomicReference<Integer>> result = service.submit(() -> {
                for (int i = 0; i < 10; i++) {
                    currentX.set(i);
                    Thread.sleep(500);
                }
                return currentX;
            });
            System.out.println(result.get(3, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            System.out.println(currentX);
        } finally {
            if (service != null) service.shutdownNow();
        }
    }
}
