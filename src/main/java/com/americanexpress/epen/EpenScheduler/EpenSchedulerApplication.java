package com.americanexpress.epen.EpenScheduler;

import com.americanexpress.epen.EpenScheduler.service.ThreadTesting;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
//@EnableBatchProcessing
@ConfigurationPropertiesScan
public class EpenSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpenSchedulerApplication.class, args);

//		ThreadTesting threadTesting = new ThreadTesting();
//		System.out.println("********************************************************************");
//		threadTesting.threadTesting();
//		System.out.println("*********************************************************************");

	}


}
