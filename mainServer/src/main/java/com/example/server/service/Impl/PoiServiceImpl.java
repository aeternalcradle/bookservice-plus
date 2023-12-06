package com.example.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.PoiMapper;
import com.example.server.pojo.Poi;
import com.example.server.service.IPoiService;
import org.springframework.stereotype.Service;

@Service
public class PoiServiceImpl extends ServiceImpl<PoiMapper, Poi>implements  IPoiService {
}
