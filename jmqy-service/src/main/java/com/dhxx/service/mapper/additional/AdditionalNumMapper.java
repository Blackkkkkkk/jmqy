package com.dhxx.service.mapper.additional;

import com.dhxx.facade.entity.additional.AdditionalNum;

import java.util.Map;

public interface AdditionalNumMapper {

    Map<String,Object> find(AdditionalNum additionalNum);

    void  save(AdditionalNum additionalNum);

    void  update(AdditionalNum additionalNum);
}
