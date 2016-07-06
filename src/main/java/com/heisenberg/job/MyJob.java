package com.heisenberg.job;

import com.heisenberg.datamodel.DataModel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Heisenberg on 7/6/16.
 */
public class MyJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(MyJob.class);
    private static int count = 0;

    private static String orginalPath = "/opt/projects/spider_weibo/src/top100Url2.txt";
    private static String outBasePath = "/opt/top100/out_";
    public MyJob(){

    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        long begin = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();

        String nowStr = sdf.format(now);

        SpiderRobot robot = new SpiderRobot();
        CollectData collectData = new CollectData();

        List<String> urls = collectData.getData(new File(orginalPath));
        List<DataModel> dataModels = robot.doWork(urls);
        collectData.writeTo(outBasePath+nowStr+".txt",dataModels);
        count ++;

        long end = System.currentTimeMillis();
        logger.info("my job is executing {} times spend {} seconds",count,(end-begin)/1000);

    }

}
