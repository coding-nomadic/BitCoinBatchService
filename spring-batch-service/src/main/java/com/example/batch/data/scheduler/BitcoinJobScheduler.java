package com.example.batch.data.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BitcoinJobScheduler {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void runMonthlyJob() {
        try {
            System.out.println("Job starting running !");
            JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
            System.out.println("Monthly job triggered at: " + System.currentTimeMillis());
        } catch (Exception exception) {
            System.err.println("Failed to run monthly job: " + exception.getMessage());
        }
    }
}