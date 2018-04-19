package com.dhxx.service.biz.transport.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dhxx.facade.entity.transport.order.BackOrder;
import com.dhxx.service.mapper.transport.order.BackOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class BackOrderBiz {

    private static Logger log = LoggerFactory.getLogger(BackOrderBiz.class);
    
	@Autowired
    private BackOrderMapper backOrderMapper;

	public PageInfo<BackOrder> list(BackOrder backOrder) {
		log.debug("BackOrderBiz.list()");
		PageHelper.startPage(backOrder.getPageNum(), backOrder.getPageSize());
        List<BackOrder> list = backOrderMapper.list(backOrder);
        PageInfo<BackOrder> pageInfo = new PageInfo<BackOrder>(list);
		return pageInfo;
	}

	public List<BackOrder> find(BackOrder backOrder) {
		log.debug("BackOrderBiz.find()");
		return backOrderMapper.list(backOrder);
	}
	
	public Long saveOrUpdate(BackOrder backOrder) {
		log.debug("BackOrderBiz.saveOrUpdate(),user="+JSONObject.toJSONString(backOrder));
		return backOrderMapper.saveOrUpdate(backOrder);
	}
	
	public Long update(BackOrder backOrder) {
		log.debug("BackOrderBiz.updateById(),user="+JSONObject.toJSONString(backOrder));
		return backOrderMapper.update(backOrder);
	}
	
}
