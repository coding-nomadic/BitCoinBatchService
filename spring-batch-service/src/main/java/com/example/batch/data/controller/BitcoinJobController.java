package com.example.batch.data.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jobs")
public class BitcoinJobController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @PostMapping("/import-bitcoin")
    public ResponseEntity<String> importCsvToMongo() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
            System.out.println("Batch job triggered successfully.");
            return ResponseEntity.ok("Bitcoin CSV import job started.");
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
            return ResponseEntity.status(500).body("Failed to start Bitcoin import job.");
        }
    }
}