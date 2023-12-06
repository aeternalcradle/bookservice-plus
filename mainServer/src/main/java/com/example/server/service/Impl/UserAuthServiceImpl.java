package com.example.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.UserAuthMapper;
import com.example.server.pojo.UserAuth;
import com.example.server.service.IUserAuthService;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth>implements IUserAuthService {
}
