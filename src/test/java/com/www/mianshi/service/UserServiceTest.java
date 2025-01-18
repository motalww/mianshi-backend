package com.www.mianshi.service;

import javax.annotation.Resource;

import com.www.mianshi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 用户服务测试
 *
 * @author @author <a href="https://github.com/motalww">www</a>
 * 
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    private final ExecutorService executorService = new ThreadPoolExecutor(20, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));

    @Test
    void userRegister() {
        long startTime = System.currentTimeMillis(); // 记录开始时间
        int batchSize = 1000; // 每批次保存的条数
        int totalBatches = 100; // 总共提交的批次数量
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < totalBatches; i++) {
            List<User> userList = new ArrayList<>(batchSize); // 每个线程任务使用独立的列表
            // 构建一个批次的数据
            for (int j = 0; j < batchSize; j++) {
                User user = new User();
                user.setUserAccount("test");
                user.setUserPassword("264b62e995454af3a74d14fcd3a14d22");
                user.setUserName("test");
                user.setUserRole("user");
                userList.add(user);
            }

            // 异步执行保存任务
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("ThreadName: " + Thread.currentThread().getName());
                userService.saveBatch(userList, batchSize); // 执行批量保存
            }, executorService);

            futureList.add(future); // 将每个 future 添加到列表中，方便等待完成
        }

        // 等待所有异步任务完成
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();

        long endTime = System.currentTimeMillis(); // 记录结束时间
        long duration = endTime - startTime; // 计算耗时（单位：毫秒）
        System.out.println("Execution time: " + duration + " milliseconds");

        // 关闭线程池
        executorService.shutdown();
    }
}
