package com.practice.spring.batch.syncprcs.job.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Component
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
    public Step transferItemStep() {
        return this.stepBuilderFactory.get("transferItemStep").tasklet(new Tasklet() {

            @Override
            @Nullable
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("Transfer task has been trigger");
                return RepeatStatus.FINISHED;
            }
            
        }).build();
    }

    @Bean
    public Job jobTransfer(Step intakeStep) {
        return this.jobBuilderFactory.get("TransferJob")
                .incrementer(new RunIdIncrementer())
                .start(intakeStep)
                .build();
    }

    // @Bean
    // public Step intakeStep() {
    //     return this.stepBuilderFactory.get("Parallel_Transfer_Step")
    //             .<Integer, Integer>chunk(10000)
    //             .reader()
    //             .writer()
    //             .build();
    // }

    // @StepScope
    // public ItemReader

}
