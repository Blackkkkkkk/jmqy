package com.dhxx.service.mapper.finance;

import com.dhxx.facade.entity.finance.Finance;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/19
 * @description
 */
public interface FinanceMapper {

    List<Finance> list(Finance finance);
}
