package com.example.server.controllers;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.mapper.PoiMapper;
import com.example.server.service.IPoiService;
import com.example.server.service.Impl.PoiServiceImpl;
import com.example.server.vo.PoiVo;
import com.example.server.pojo.Poi;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/poi")
public class PoiController {
    //spring依赖注入，它用于告诉Spring容器在需要使用PoiServiceImpl对象时，将其自动注入到PoiService字段中
    @Autowired
    private IPoiService poiService;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        log.info("my info,pageNum={} pageSize={}",pageNum,pageSize);
        Page<Poi> page = new Page<Poi>(pageNum,pageSize);
        IPage<Poi> pageResult = poiService.page(page);
        List voList = pageResult.getRecords().stream().map(poi->{
            PoiVo poiVo = new PoiVo();
            BeanUtils.copyProperties(poi,poiVo);
            poiVo.key=poiVo.id;
            return poiVo;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
   }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){

        Poi poi = poiService.getById(id);
        if(poi==null){
            throw  PoiException.NotFound();
        }
        return Result.success(poi);
    }

    @PostMapping("add")
    public Result add(@RequestBody Poi poi) {
        log.info("poi add, name = {}", poi.getName());
        Poi existingPoi = poiService.getOne(new QueryWrapper<Poi>().eq("book_id", poi.bookId));
        if (existingPoi != null) {
            existingPoi.setNum(existingPoi.getNum() + poi.getNum());
            poiService.updateById(existingPoi);
            return Result.success(existingPoi);
        } else {
            poiService.save(poi);
            return Result.success(poi);
        }
    }

    @PutMapping("edit/{id}")
    public Result edit(@RequestBody Poi poi ,@PathVariable int id){
        log.info("poi edit,name={} price={} num={}",poi.name,poi.price,poi.num);
        poi.setId(id);
        poiService.updateById(poi);
        return Result.success(poi);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("my detail,id={}",id);
        poiService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("delete-by-name/{name}")
    public Result deleteByName(@PathVariable String name) {
        log.info("Delete Poi records by name: {}", name);
        List<Poi> poisToDelete = poiService.list(new QueryWrapper<Poi>().eq("username", name));

        if (!poisToDelete.isEmpty()) {
            List<Integer> idsToDelete = poisToDelete.stream().map(Poi::getId).collect(Collectors.toList());
            poiService.removeByIds(idsToDelete);
            return Result.success("Deleted Poi records for username: " + name);
        } else {
            return Result.success("No matching Poi records found for username: " + name);
        }
    }
}
