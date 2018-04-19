package com.dhxx.service.service.line;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.line.Line;
import com.dhxx.facade.service.line.LineFacade;
import com.dhxx.service.biz.line.LineBiz;
/**
 * 
 * @author hanrs
 *
 */
@Service(protocol = {"dubbo"})
public class LineFacadeImpl implements LineFacade {
    
	private static Logger log = LoggerFactory.getLogger(LineFacadeImpl.class);
	
    @Autowired
    private LineBiz lineBiz;

	@Override
	public Object list(Line line) {
		log.debug("LineFacadeImpl.list()");
		return lineBiz.list(line);
	}

	@Override
	public Object find(Line line) {
		log.debug("LineFacadeImpl.find()");
		return lineBiz.find(line);
	}

	@Override
	public Object save(Line line) {
		log.debug("LineFacadeImpl.save()");
		return lineBiz.save(line);
	}
	
	@Override
	public Object saveOrUpdate(Line line) {
		log.debug("LineFacadeImpl.saveOrUpdate()");
		return lineBiz.saveOrUpdate(line);
	}
	
	@Override
	public Object update(Line line) {
		log.debug("LineFacadeImpl.updateById()");
		return lineBiz.update(line);
	}
}
