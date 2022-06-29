package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    public static void main(String [] args) throws InterruptedException {
//        simple();
//        tryLock();
        tryLockTime();
    }

    private static void tryLockTime() throws InterruptedException {
        Lock lock = new ReentrantLock();
        new Thread(() -> printMessage(lock)).start();
        Thread.sleep(100);
        if(lock.tryLock(200, TimeUnit.MILLISECONDS)) {
            try {
                System.out.println("Lock obtained, entering protected code");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Unable to acquire lock, doing something else");
        }
    }

    private static void tryLock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        new Thread(() -> printMessage(lock)).start();
        Thread.sleep(100);
        if(lock.tryLock()) {
            try {
                System.out.println("Lock obtained, entering protected code");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Unable to acquire lock, doing something else");
        }
    }

    public static void printMessage(Lock lock){
        try {
            lock.lock();
            System.out.println("Locked code");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private static void simple() {
        // Implementation #1 with a synchronized block
        synchronized(Locks.class) {
            System.out.println("Sync code goes here");
        }

        // Implementation #2 with a Lock
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            System.out.println("Loc code goes here");
        } finally {
            lock.unlock();
        }
    }
}
