package com.dhxx.service.mapper.line;

import java.util.List;

import com.dhxx.facade.entity.line.Line;

public interface LineMapper {

	List<Line> list(Line line);
	
	Long save(Line line);
	
	Long saveOrUpdate(Line line);

	Long update(Line line);
	
}
