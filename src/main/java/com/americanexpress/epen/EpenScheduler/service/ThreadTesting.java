package com.americanexpress.epen.EpenScheduler.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTesting {

    private LocalDateTime pastTime;

    public void threadTesting(){
        pastTime  = LocalDateTime.now();
//        System.out.println("Current date and time: " + pastTime );

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Submit 20 tasks for execution
        for (int i = 1; i <= 20; i++) {
            int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    print(finalI);
                }
            });
        }

        // Shutdown the executor service
        executorService.shutdown();
    }

    private void print(int finalI){
     synchronized (this)
        {
            System.out.println(finalI + " is being executed by " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000); // Simulate a task taking some time to execute
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Task " + finalI + " was interrupted");
            }
            System.out.println(finalI + " completed by " + Thread.currentThread().getName());

            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(pastTime, currentTime);
            long seconds = duration.getSeconds();
            System.out.println("Duration in seconds: " + seconds);
        }

    }
}
