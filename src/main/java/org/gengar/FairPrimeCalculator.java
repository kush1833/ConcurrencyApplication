package org.gengar;

import java.util.ArrayList;
import java.util.List;

public class FairPrimeCalculator implements Runnable {
    private int primeCount = 0;
    private volatile long num = 1;
    public synchronized void  incrementCount() {
        primeCount++;
    }
    synchronized long getNumberToCheck(){
        return num++;
    }
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(true) {
            long val = getNumberToCheck();
            if(val > 100000000)
                break;
            boolean isPrime = true;
            for (int i = 2; i <= Math.sqrt(val + 1); i++) {
                if (val % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                incrementCount();
            }
        }
        System.out.println("Time taken = " + (System.currentTimeMillis() - startTime));
    }
    public static void main(String[] args) throws InterruptedException {
        FairPrimeCalculator fairPrimeCalculator = new FairPrimeCalculator();
        List<Thread> childThreads = new ArrayList<>();
        for(int i=1; i<= 10; i++) {
            Thread t = new Thread(fairPrimeCalculator, "thread: "+i);
            childThreads.add(t);
            t.start();
        }
        for(Thread t: childThreads) {
            t.join();
        }
        System.out.println("Total primes from 1 to " + 100000000 + " = " + fairPrimeCalculator.primeCount);

    }
}