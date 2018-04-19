package com.dhxx.facade.service.line;

import com.dhxx.facade.entity.line.Line;

/**
 * <p> 类说明 </p>
 * @author hanrs
 * Date: 2017年09月28日
 * @version 1.01
 * 线路管理interface
 */
public interface LineFacade {
	
	Object list(Line line);
	
	Object find(Line line);
	
	Object save(Line line);
	
	Object update(Line line);

	Object saveOrUpdate(Line line);
}
