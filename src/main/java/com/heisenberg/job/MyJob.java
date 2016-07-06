package com.heisenberg.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Heisenberg on 7/6/16.
 */
public class MyJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(MyJob.class);
    private static int count = 0;
    public MyJob(){

    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        count ++;
        logger.info("my job is executing {} times",count);
    }
}
