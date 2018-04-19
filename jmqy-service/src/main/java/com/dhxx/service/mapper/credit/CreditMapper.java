package com.dhxx.service.mapper.credit;

import com.dhxx.facade.entity.credit.Credit;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/16
 * @description
 */
public interface CreditMapper {

    List<Credit> list(Credit credit);

    void update(Credit credit);

    Credit findOne(Credit credit);

    void save(Credit credit);

    List<Credit> companyCreditList(Credit credit);
}
