package com.dhxx.facade.service.remind;

import com.dhxx.facade.entity.remind.Remind;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
public interface RemindFacade {

    Object list(Remind remind);

    void save(Remind remind);
    
    void update(Remind remind);
    
    Object statistics(Remind remind);

    void saveOne(Remind remind);

}
