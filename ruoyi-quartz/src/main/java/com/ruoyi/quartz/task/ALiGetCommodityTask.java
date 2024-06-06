package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1688定时获取商品Task
 *
 * @author ruoyi
 */
@Component("aLiGetCommodityTask")
public class ALiGetCommodityTask {

    /*
    * 每日调取订单接口获取订单列表*/
    public void ryMultipleParams(String s, Boolean b, Integer j)
    {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                4,8,1,
                TimeUnit.SECONDS, // 时间单位
                new ArrayBlockingQueue<>(5), // 任务队列
                new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " is running.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " is completed.");
            });
        }
        // 关闭线程池
        executor.shutdown();
    }


}
