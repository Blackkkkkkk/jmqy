package com.dhxx.service.biz.line;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.entity.line.Line;
import com.dhxx.service.mapper.line.LineMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class LineBiz {

    private static Logger log = LoggerFactory.getLogger(LineBiz.class);
    
	@Autowired
    private LineMapper lineMapper;

	public PageInfo<Line> list(Line line) {
		log.debug("LineBiz.list()");
		PageHelper.startPage(line.getPageNum(), line.getPageSize());
        List<Line> list = lineMapper.list(line);
        PageInfo<Line> pageInfo = new PageInfo<Line>(list);
		return pageInfo;
	}

	public List<Line> find(Line line) {
		log.debug("LineBiz.find()");
		return lineMapper.list(line);
	}
	
	public Line save(Line line) {
		log.debug("LineBiz.save(),user="+JSONObject.toJSONString(line));
		lineMapper.save(line);
		return line;
	}
	
	public Line saveOrUpdate(Line line) {
		log.debug("LineBiz.saveOrUpdate(),user="+JSONObject.toJSONString(line));
		lineMapper.saveOrUpdate(line);
		return line;
	}
	
	public Long update(Line line) {
		log.debug("LineBiz.update(),user="+JSONObject.toJSONString(line));
		return lineMapper.update(line);
	}
	
}
