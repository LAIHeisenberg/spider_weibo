package com.heisenberg;

import com.heisenberg.job.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Heisenberg on 7/6/16.
 */
public class TestQuartz {
    private static Logger logger = LoggerFactory.getLogger(TestQuartz.class);
    public static void main(String[] args) throws ParseException {
        JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();

        /*Trigger trigger = TriggerBuilder.newTrigger().
                withIdentity("myTRigger","group1").
                startNow().
                withSchedule(SimpleScheduleBuilder.simpleSchedule().
                withIntervalInSeconds(5).repeatForever()).build();*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = sdf.parse("2016-07-06 20:00:00");

        Trigger trigger1 = TriggerBuilder.newTrigger().
                            withIdentity("trigger2","group1").
                            startAt(date).
                            withSchedule(
                                    SimpleScheduleBuilder.
                                    simpleSchedule().
                                    withIntervalInHours(1).
                                    repeatForever()
                            ).build();

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(job,trigger1);
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
