package com.inglab.klse.scheduler;

import com.inglab.klse.service.StockService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class StockJob implements Job {

    private final StockService service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            service.retrieveStock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
