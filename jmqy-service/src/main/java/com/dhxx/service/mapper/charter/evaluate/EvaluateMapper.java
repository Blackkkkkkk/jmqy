package com.dhxx.service.mapper.charter.evaluate;

import java.util.List;

import com.dhxx.facade.entity.charter.evaluate.Evaluate;

public interface EvaluateMapper {
	
	void save(Evaluate evaluate);

	List<Evaluate> list(Evaluate evaluate);
}
