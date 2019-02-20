package com.telangel.springboottask.task;

import com.telangel.springboottask.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @项目名称： springboot-task
 * @类名称： MyTask
 * @类描述：
 * @author： lid
 * @date： 2019/2/20 10:40
 */
@Component
public class MyTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyTask.class);

    private static final long SECOND = 1000;

    @Autowired
    UserService userService;

    /**
     * 固定频率执行任务
     * 固定间隔3秒，可以引用变量
     * fixedRate：以每次开始时间作为测量，间隔固定时间
     */
    @Scheduled(fixedRate = 5 * SECOND)
    @Async
    public void task1() {
        LOGGER.info("当前时间：{}\t\t任务：fixedRate task，每3秒执行一次", System.currentTimeMillis());
        userService.test();
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    /**
//     *  固定延迟3秒，从前一次任务结束开始计算，延迟3秒执行
//     */
//    @Scheduled(fixedDelay = 3 * SECOND)
//    public void task2(){
//        LOGGER.info("当前时间：{}\t\t任务：fixedDelay task，每延迟3秒执行一次", System.currentTimeMillis());
//    }

    /**
     * cron表达式，每5秒执行
     */
    @Scheduled(cron = "*/5 * * * * ?")
    @Async
    public void task3() {
        LOGGER.info("当前时间：{}\t\t任务：cron task，每5秒执行一次", System.currentTimeMillis());
    }

}
