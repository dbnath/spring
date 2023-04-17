package com.practice.spring.batch.syncprcs.job.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class BatchConfig {

    private final DataSource sourceDs;
    private final DataSource dataSource;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(
            @Qualifier("sourceDbDatasource") DataSource sourceDs, @Qualifier("destDbDatasource") DataSource destDs,
            JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.sourceDs = sourceDs;
        this.dataSource = destDs;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }


    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("TransferJob")
                .incrementer(new RunIdIncrementer())                
                .start(startStep())
                .next(intakeCashIn())
                .next(intakeCashOutNonRet())
                .next(intakeCashOutRet())
                .next(transferItemStep())
                .build();
    }

    // @Bean
    // public Job job() {
    //     return this.jobBuilderFactory.get("TransferJob")
    //             .incrementer(new RunIdIncrementer())                
    //             .start(startStep())
    //             .split(new SimpleAsyncTaskExecutor())
    //             .add(flow1(), flow2(), flow3(), flow4())              
    //             .end()  
    //             .build();
    // }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow1").start(intakeCashIn()).build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2").start(intakeCashOutNonRet()).build();
    }

    @Bean
    public Flow flow3() {
        return new FlowBuilder<SimpleFlow>("flow3").start(intakeCashOutRet()).build();
    }

    @Bean
    public Flow flow4() {
        return new FlowBuilder<SimpleFlow>("flow4").start(transferItemStep()).build();
    }

    @Bean
    public Step startStep() {
        return this.stepBuilderFactory.get("startStep").tasklet((contribution, chunkContext) -> {
            log.info("Start Step task has been trigger");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step intakeCashIn() {
        return this.stepBuilderFactory.get("intakeCashIn").tasklet(new Tasklet() {
            @Override
            @Nullable
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("Intake CashIn task has been trigger");
                Thread.sleep(10000);
                return RepeatStatus.FINISHED;
            }
            
        }).build();
    }

    @Bean
    public Step intakeCashOutNonRet() {
        return this.stepBuilderFactory.get("intakeCashOutNonRet").tasklet(new Tasklet() {
            @Override
            @Nullable
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("Intake CashOut(Non-Retr) task has been trigger");
                Thread.sleep(10000);
                return RepeatStatus.FINISHED;
            }
            
        }).build();
    }

    @Bean
    public Step intakeCashOutRet() {
        return this.stepBuilderFactory.get("intakeCashOutRet").tasklet(new Tasklet() {
            @Override
            @Nullable
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("Intake CashOut(Retr) task has been trigger");
                Thread.sleep(10000);
                return RepeatStatus.FINISHED;
            }
            
        }).build();
    }

    @Bean
    public Step transferItemStep() {
        return this.stepBuilderFactory.get("transferItemStep").tasklet(new Tasklet() {

            @Override
            @Nullable
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("Transfer task has been trigger");
                Thread.sleep(10000);
                return RepeatStatus.FINISHED;
            }
            
        }).build();
    }
}
