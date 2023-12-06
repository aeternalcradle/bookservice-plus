package com.example.server.service.Impl;

import com.example.server.service.ITimerService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class TimerServiceImpl implements ITimerService {
    long launchTime = 0;
    @Override
    public long startTimer() {
        if(launchTime == 0) {
            this.launchTime = System.currentTimeMillis();
        }
        return launchTime;
    }

    @Override
    public long stopTimer() {
        if (launchTime > 0) {
            long time_interval = (System.currentTimeMillis() - launchTime);
            launchTime = 0;
            return time_interval / 1000; // 将毫秒转换为秒
        } else {
            return 0; // 如果计时器未启动，则返回0
        }
    }

    @Override
    public long getCurrentTime() {
        if (launchTime > 0) {
            return (System.currentTimeMillis() - launchTime) / 1000; // 获取当前计时时间（以秒为单位）
        } else {
            return 0; // 如果计时器未启动，则返回0
        }
    }
}
