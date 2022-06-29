package org.example;

import java.util.concurrent.*;

public class WaitingForAllTasksToFinish {
    public static void main(String[] args) throws Exception {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();

            Future<Integer> result = service.submit(() -> 2 + 3);
            Future<Integer> result2 = service.submit(() -> 3 + 4);
            System.out.println(result.get(5, TimeUnit.SECONDS));
            System.out.println(result2.get(5, TimeUnit.SECONDS));
        }
        finally{
            if(service != null) service.shutdownNow();
        }

        if(service != null) {
            service.awaitTermination(1, TimeUnit.MINUTES);
            // Check whether all tasks are finished
            if(service.isTerminated()) System.out.println("Finished!");
            else System.out.println("At least one task is still running");
        }
    }
}
