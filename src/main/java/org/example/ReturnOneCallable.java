package org.example;

import java.util.concurrent.*;

public class ReturnOneCallable {

    private static int currentX = 0;

    public static void main(String[] args) throws Exception {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();

            Future<Integer> result = service.submit(() -> {
                int x = currentX;
                for (int i = 0; i < 10; i++){
                    x = i;
                    currentX = i;
                    Thread.sleep(2000);
                }
                return x;
            });
            System.out.println(result.get(10, TimeUnit.SECONDS));
        }
        catch (TimeoutException e){
            System.out.println(currentX);
        }
        finally{
            if(service != null) service.shutdownNow();
        }
    }
}
