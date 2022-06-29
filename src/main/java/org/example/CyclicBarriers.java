package org.example;

import java.util.concurrent.*;

public class CyclicBarriers {

    private void removeLions() {System.out.println("Removing lions");}
    private void cleanPen() {System.out.println("Cleaning the pen");}
    private void addLions() {System.out.println("Adding lions");}

    public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            removeLions();
            c1.await();
            cleanPen();
            c2.await();
            addLions();
        } catch (InterruptedException | BrokenBarrierException e) {
            // Handle checked exceptions here
        }
    }
    public static void main(String[] args) {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);
            var manager = new CyclicBarriers();
            var c1 = new CyclicBarrier(4);
            var c2 = new CyclicBarrier(4,
                    () -> System.out.println("*** Pen Cleaned!"));
            for (int i = 0; i < 4; i++)
                service.submit(() -> manager.performTask(c1, c2));
        } finally {
            if (service != null) service.shutdown();
        }
    }

    private void performTask() {
        removeLions();
        cleanPen();
        addLions();
    }
}
