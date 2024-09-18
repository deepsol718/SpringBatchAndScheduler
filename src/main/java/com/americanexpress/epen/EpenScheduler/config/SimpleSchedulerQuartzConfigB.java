package com.americanexpress.epen.EpenScheduler.config;

import com.americanexpress.epen.EpenScheduler.scheduler.SimpleSchedulerJobA;
import com.americanexpress.epen.EpenScheduler.scheduler.SimpleSchedulerJobB;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
@Configuration
public class SimpleSchedulerQuartzConfigB {
    @Autowired
    QuartzSchedulerConfig quartzSchedulerConfig;

    @Autowired
//    @Qualifier("quartzDataSource")
    private DataSource dataSource;

    private String cronExpression = "0 0/1 * * * ?";


    // add cae automation process beans - start
    @Bean(name = "SimpleSchedulerJobBFactory")
    public SchedulerFactoryBean scheduler(@Qualifier("SimpleSchedulerJobB") JobDetail AddCaseSchedulerJob,
                                          @Qualifier("SimpleSchedulerJobBCron") Trigger AddCaseSchedulerCron
    ) throws Exception {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setQuartzProperties(quartzSchedulerConfig.quartzProperties());
        schedulerFactory.setJobFactory(quartzSchedulerConfig.jobFactory());
        schedulerFactory.setDataSource(dataSource);
        return schedulerFactory;
    }

    @Bean
    public Scheduler getSimpleSchedulerB(@Qualifier("SimpleSchedulerJobBFactory") SchedulerFactoryBean addCaseSchedulerFactory,
                                         @Qualifier("SimpleSchedulerJobBCron") Trigger AddCaseSchedulerCron,
                                         @Qualifier("SimpleSchedulerJobB") JobDetail AddCaseSchedulerJob)
            throws Exception {
        Scheduler scheduler = addCaseSchedulerFactory.getScheduler();

        TriggerKey key = new TriggerKey("AddCaseSchedulerJob" + "AddCase", "DEFAULT");

        CronTrigger crontrigger = (CronTrigger) scheduler.getTrigger(key);

        JobKey jobKey = new JobKey("SimpleSchedulerJobB", "DEFAULT");

// Delete the job if it already exists
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }

        if (crontrigger == null) {
            scheduler.scheduleJob(AddCaseSchedulerJob, AddCaseSchedulerCron);
        }
        if (crontrigger != null && !crontrigger.getCronExpression().equalsIgnoreCase(cronExpression)) {
            scheduler.rescheduleJob(key, AddCaseSchedulerCron);
        }
        scheduler.start();
        return scheduler;
    }

    @Bean(name = "SimpleSchedulerJobB")
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SimpleSchedulerJobB.class).storeDurably()
                .withIdentity("SimpleSchedulerJobB", "DEFAULT")
                .requestRecovery(true).storeDurably(true)
                .withDescription("add case automation quartz scheduled job").build();
    }

    @Bean(name = "SimpleSchedulerJobBCron")
    public Trigger trigger() {
        return TriggerBuilder.newTrigger().withIdentity("SimpleSchedulerJobB" + "AddCase", "DEFAULT")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .forJob("SimpleSchedulerJobB", "DEFAULT").startNow().build();
    }
}

