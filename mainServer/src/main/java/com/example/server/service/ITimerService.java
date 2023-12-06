package com.example.server.service;

public interface ITimerService {

    // 初始化计时器
    long startTimer();

    // 停止计时器并返回计时时间（以秒为单位）
    long stopTimer();

    // 获取当前计时时间（以秒为单位），但不停止计时器
    long getCurrentTime();
}
