package com.americanexpress.epen.EpenScheduler.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SimpleSchedulerJobA implements Job {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    org.springframework.batch.core.Job fileJob;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("This is a scheduled job A");
        startBatch();
    }

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
