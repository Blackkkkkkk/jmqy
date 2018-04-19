package com.dhxx.service.service.remind;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.service.biz.remind.RemindBiz;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
@Service(protocol = {"dubbo"})
public class RemindFacadeImpl implements RemindFacade {

    @Autowired
    private RemindBiz remindBiz;

    @Override
    public Object list(Remind remind) {
        return remindBiz.list(remind);
    }

    @Override
    public void save(Remind remind) {
        remindBiz.save(remind);
    }

	@Override
	public Object statistics(Remind remind) {
		return remindBiz.statistics(remind);
	}

	@Override
	public void update(Remind remind) {
		remindBiz.update(remind);		
	}

    @Override
    public void saveOne(Remind remind) {
        remindBiz.saveOne(remind);
    }
}
