package com.americanexpress.epen.EpenScheduler.config;

import com.americanexpress.epen.EpenScheduler.scheduler.SpringJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Properties;

@Configuration
public class QuartzSchedulerConfig {

    @Value("org.quartz.impl.jdbcjobstore.JobStoreTX")
    private String org_quartz_jobStore_class;

    @Value("epen_qrtz.qrtz_") private String
            org_quartz_jobStore_tablePrefix;

    @Value("${org.quartz.jobStore.driverDelegateClass}")
    private String org_quartz_jobStore_driverDelegateClass;

    @Value("${org.quartz.jobStore.isClustered}") private String org_quartz_jobStore_isClustered;

    @Value("${org.quartz.jobStore.misfireThreshold}") private String org_quartz_jobStore_misfireThreshold;

    @Value("${org.quartz.threadPool.threadPriority}")
    private String org_quartz_jobStore_threadPriority;

    @Value("${org.quartz.threadPool.class}")
    private String org_quartz_threadPool_class;

    @Value("${org.quartz.threadPool.threadCount}")
    private String org_quartz_threadPool_threadCount;

    @Value("${org.quartz.jobStore.clusterCheckinInterval}")
    private String org_quartz_jobStore_clusterCheckinInterval;

    /*
     * @Value("${org.quartz.jobStore.useProperties}") private String
     * org_quartz_jobStore_useProperties;
     */

    @Value("${org.quartz.scheduler.instanceId}")
    private String org_quartz_scheduler_instanceId;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory jobFactory() {
        SpringJobFactory jobFactory = new SpringJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean(name = "QuartzProperties")
    public Properties quartzProperties() {

        Properties quartzProperties = new Properties();
        quartzProperties.put("org.quartz.jobStore.class", org_quartz_jobStore_class);
        quartzProperties.put("org.quartz.jobStore.tablePrefix", org_quartz_jobStore_tablePrefix);
        quartzProperties.put("org.quartz.jobStore.driverDelegateClass", org_quartz_jobStore_driverDelegateClass);
        quartzProperties.put("org.quartz.jobStore.isClustered", org_quartz_jobStore_isClustered);
        quartzProperties.put("org.quartz.jobStore.clusterCheckinInterval", org_quartz_jobStore_clusterCheckinInterval);
        quartzProperties.put("org.quartz.jobStore.useProperties", false);
        quartzProperties.put("org.quartz.jobStore.misfireThreshold", org_quartz_jobStore_misfireThreshold);
        quartzProperties.put("org.quartz.threadPool.threadPriority", org_quartz_jobStore_threadPriority);
        // quartzProperties.put("org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializingThread", true);
        quartzProperties.put("org.quartz.threadPool.class", org_quartz_threadPool_class);
        quartzProperties.put("org.quartz.threadPool.threadCount", org_quartz_threadPool_threadCount);
        quartzProperties.put("org.quartz.scheduler.instanceId", org_quartz_scheduler_instanceId);
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.provider", "HikariCP");

//         **Define the DataSource properties for Quartz**
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.driver", "org.postgresql.Driver");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.URL", "jdbc:postgresql://localhost:5432/ependb");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.user", "postgres");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.password", "1234");
        quartzProperties.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
//
//        // **Specify the DataSource name**
        quartzProperties.put("org.quartz.jobStore.dataSource", "quartzDataSource");



        return quartzProperties;

    }
}
