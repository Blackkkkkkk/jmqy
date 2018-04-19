package com.dhxx.service.service.finance;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.finance.Finance;
import com.dhxx.facade.service.finance.FinanceFacade;
import com.dhxx.service.biz.finance.FinanceBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/19
 * @description
 */
@Service(protocol = {"dubbo"})
public class FinanceFacadeImpl implements FinanceFacade {

    @Autowired
    private FinanceBiz financeBiz;

    @Override
    public Object list(Finance finance) {
        return financeBiz.list(finance);
    }

}
