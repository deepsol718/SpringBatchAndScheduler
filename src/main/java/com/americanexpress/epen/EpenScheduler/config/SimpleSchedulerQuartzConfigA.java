package com.americanexpress.epen.EpenScheduler.config;

import com.americanexpress.epen.EpenScheduler.scheduler.SimpleSchedulerJobA;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class SimpleSchedulerQuartzConfigA {

    @Autowired
    QuartzSchedulerConfig quartzSchedulerConfig;

    @Autowired
//    @Qualifier("quartzDataSource")
    private DataSource dataSource;

    private String cronExpression = "0 0/1 * * * ?";


    // add cae automation process beans - start
    @Bean(name = "AddCaseSchedulerFactory")
    public SchedulerFactoryBean scheduler(@Qualifier("AddCaseSchedulerJob") JobDetail AddCaseSchedulerJob,
                                          @Qualifier("AddCaseSchedulerCron") Trigger AddCaseSchedulerCron
                                          ) throws Exception {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setQuartzProperties(quartzSchedulerConfig.quartzProperties());
        schedulerFactory.setJobFactory(quartzSchedulerConfig.jobFactory());
        schedulerFactory.setDataSource(dataSource);
        return schedulerFactory;
    }

    @Bean
    public Scheduler getaddCaseScheduler(@Qualifier("AddCaseSchedulerFactory") SchedulerFactoryBean addCaseSchedulerFactory,
                                         @Qualifier("AddCaseSchedulerCron") Trigger AddCaseSchedulerCron,
                                         @Qualifier("AddCaseSchedulerJob") JobDetail AddCaseSchedulerJob)
            throws Exception {
        Scheduler scheduler = addCaseSchedulerFactory.getScheduler();

        TriggerKey key = new TriggerKey("AddCaseSchedulerJob" + "AddCase", "DEFAULT");

        CronTrigger crontrigger = (CronTrigger) scheduler.getTrigger(key);

        scheduler.addJob(AddCaseSchedulerJob, true);

        if (crontrigger == null) {
            scheduler.scheduleJob(AddCaseSchedulerJob, AddCaseSchedulerCron);
        }
        if (crontrigger != null && !crontrigger.getCronExpression().equalsIgnoreCase(cronExpression)) {
            scheduler.rescheduleJob(key, AddCaseSchedulerCron);
        }
        scheduler.start();
        return scheduler;
    }

    @Bean(name = "AddCaseSchedulerJob")
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SimpleSchedulerJobA.class).storeDurably()
                .withIdentity("AddCaseSchedulerJob", "DEFAULT")
                .requestRecovery(true).storeDurably(true)
                .withDescription("add case automation quartz scheduled job").build();
    }

    @Bean(name = "AddCaseSchedulerCron")
    public Trigger trigger() {
        return TriggerBuilder.newTrigger().withIdentity("AddCaseSchedulerJob" + "AddCase", "DEFAULT")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .forJob("AddCaseSchedulerJob", "DEFAULT").startNow().build();
    }
}
