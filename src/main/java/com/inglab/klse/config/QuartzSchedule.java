package com.inglab.klse.config;

import com.inglab.klse.scheduler.StockJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;
@Configuration
public class QuartzSchedule {

    @Bean
    public JobDetail stockJobDetail() {
        return JobBuilder.newJob(StockJob.class)
                .withIdentity("stockSummaryJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger stockJobTrigger() {
        //String cronExpression = "0 0 9 * * ?"; // every 9am
        String cronExpression = "0/15 * * * * ?"; // 30 sec int

        return TriggerBuilder.newTrigger()
                .forJob(stockJobDetail())
                .withIdentity("stockSummaryTrigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronExpression)
                        .inTimeZone(TimeZone.getTimeZone("Asia/Kuala_Lumpur")))
                .build();
    }

}
