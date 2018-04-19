package com.dhxx.facade.service.additional;

import com.dhxx.facade.entity.additional.AdditionalNum;

import java.util.Map;

public interface AdditionalNumFacade {

    Map<String,Object> find(AdditionalNum additionalNum);

    void  save(AdditionalNum additionalNum);

    void  update(AdditionalNum additionalNum);
}
