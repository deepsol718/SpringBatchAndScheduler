package com.americanexpress.epen.EpenScheduler.config;

import com.americanexpress.epen.EpenScheduler.batchitems.BatchItemProcessorNames;
import com.americanexpress.epen.EpenScheduler.batchitems.BatchItemReaderNames;
import com.americanexpress.epen.EpenScheduler.batchitems.BatchItemWriterNames;
import com.americanexpress.epen.EpenScheduler.model.IndianName;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class IndianNameBatchJob {
//    @Autowired
//    private JobBuilder jobBuilder;
//    @Autowired
//    private StepBuilder stepBuilder;
    @Autowired
    @Qualifier("t325DataSource")
    private DataSource t325DataSource;

    @Value("classpath:org/springframework/batch/core/schema-drop-postgresql.sql")
    private Resource dropRepositoryTables;

    @Value("classpath:org/springframework/batch/core/schema-postgresql.sql")
    private Resource dataRepositorySchema;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager(){
        return new JpaTransactionManager();
    }

    @Bean(name = "jobRepository")
    public JobRepository getJobRepository() throws Exception{
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(this.t325DataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

//    spring.datasource.driver-class-name = org.postgresql.Driver
//    spring.datasource.url = jdbc:postgresql://localhost:5432/ependb

//    spring.datasource.username = postgres
//    spring.datasource.password = 1234
//    public DataSource t325DataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/ependb?currentSchema=t325");
////        jdbc:postgresql://localhost:5432/ependb?currentSchema=t325
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("1234");
//        dataSourceInitializer(dataSource);
//        return dataSource;
//    }
//
//    public DataSourceInitializer dataSourceInitializer(DataSource dataSource){
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//        databasePopulator.addScript(dropRepositoryTables);
//        databasePopulator.addScript(dataRepositorySchema);
//        databasePopulator.setIgnoreFailedDrops(false);
//
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator);
//
//        return initializer;
//    }


    @Bean
    public ItemReader<IndianName> itemReader(){ return new BatchItemReaderNames();
    }
    @Bean
    public ItemWriter<IndianName> itemWriter(){ return new BatchItemWriterNames();
    }

    @Bean
    public ItemProcessor<IndianName, IndianName> itemProcessor(){return new BatchItemProcessorNames();
    }
    @Bean
    protected Step fileStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager){
        return new StepBuilder("stepOne", jobRepository)
                .<IndianName, IndianName>chunk(700, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job fileJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) throws IOException {
        return new JobBuilder("jobOne", jobRepository)
                .start(fileStep(jobRepository, transactionManager))
                .build();
    }

}
