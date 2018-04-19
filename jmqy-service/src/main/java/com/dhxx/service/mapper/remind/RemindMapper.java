package com.dhxx.service.mapper.remind;

import com.dhxx.facade.entity.remind.Remind;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
public interface RemindMapper {

    List<Remind> list(Remind remind);

    void save(List<Remind> remindList);

	Integer statistics(Remind remind);

	void update(Remind remind);

    void saveOne(Remind remind);
}
