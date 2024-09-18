package com.americanexpress.epen.EpenScheduler.controller;

import com.americanexpress.epen.EpenScheduler.service.DBTesting;
import com.americanexpress.epen.EpenScheduler.service.RestTemplateDemo;
import com.americanexpress.epen.EpenScheduler.service.WebClientDemo;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.util.Date;

@RestController
@RequestMapping("/epen")
public class Status {

    @Autowired
    DBTesting dbTesting;

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private RestTemplateDemo restTemplateDemo;

    @Autowired
    private WebClientDemo webClientDemo;
    
    @Autowired
    Job fileJob;

    @GetMapping("/application")
    public String applicationUp(){
        String name = dbTesting.getDataFromDB().get(0);
        return "application is up with data: "+name;
    }

    @GetMapping("/testrestexchange")
    public String testRestExchange(){
        return restTemplateDemo.getUsersPage2();
    }

    @GetMapping("/testwebclient")
    public String testWebClient(){
        Mono<String> response = webClientDemo.getUsersPage2();
        response.subscribe(System.out::println);

        return "all ok";
    }
    @GetMapping("/startbatch")
    public String startBatch(){
        System.out.println("starting the batch");
        try {
            Date date = new Date();
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("launchDate", date)
                    .addString("id","koibheid")
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(fileJob, jobParameters);
            
        }
        catch (Exception e){
            System.out.println("exception aa gya: "+e.getMessage());
        }
        System.out.println("batch has been finished");
        return "all ok";
    }
}
