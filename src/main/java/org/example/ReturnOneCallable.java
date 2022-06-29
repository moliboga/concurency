package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReturnOneCallable {

    public static void main(String[] args) throws Exception {
        ExecutorService service = null;
        AtomicInteger currentX = new AtomicInteger();
        try {
            service = Executors.newSingleThreadExecutor();

            Future<Integer> result = service.submit(() -> {
                int x = currentX.get();
                for (int i = 0; i < 10; i++){
                    x = i;
                    currentX.set(i);
                    Thread.sleep(2000);
                }
                return x;
            });
            System.out.println(result.get(10, TimeUnit.SECONDS));
        }
        catch (TimeoutException e){
            System.out.println(currentX.get());
        }
        finally{
            if(service != null) service.shutdownNow();
        }
    }
}
