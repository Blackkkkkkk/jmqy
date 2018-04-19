package com.dhxx.service.biz.remind;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.user.User;
import com.dhxx.service.mapper.remind.RemindMapper;
import com.dhxx.service.mapper.user.UserMapper;
import com.dhxx.service.service.transport.car.CarFacadeImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
@Service
@Transactional
public class RemindBiz {
    private static Logger log = LoggerFactory.getLogger(CarFacadeImpl.class);

    @Autowired
    private RemindMapper remindMapper;
    
    @Autowired
    private UserMapper userMapper;

    public PageInfo<Remind> list(Remind remind) {
        PageHelper.startPage(remind.getPageNum(), remind.getPageSize());
        List<Remind> list = remindMapper.list(remind);
        PageInfo<Remind> pageInfo = new PageInfo<Remind>(list);
        return pageInfo;
    }

    public void save(Remind remind) {
        List<Remind> list = new ArrayList<Remind>();
        List<String> idList = remind.getIdList();
        if(!CollectionUtils.isEmpty(idList)){
            for(String ids:idList){
                String[] idArr = ids.split("@");
                Remind r = new Remind();
                if(idArr.length>0) {
                    if (idArr[0].length() > 0) {
                    	if (idArr[0].indexOf("-") == -1) {
                    		r.setUserId(Long.valueOf(idArr[0]));
                    		r.setRemindTime(new Date());
                    		r.setType(remind.getType());
                    		if(idArr.length>1) {
                                r.setOrderId(Long.valueOf(idArr[1]));
                            }
                    		list.add(r);
                        }else {
                        	User user = new User();
                        	user.setCompanyCode(idArr[0]);
                        	user.setStatus(0);
                        	List<User> users = userMapper.find(user);
                        	for (User u : users) {
                        		Remind rr = new Remind();
                        		rr.setUserId(u.getId());
                        		rr.setRemindTime(new Date());
                        		rr.setType(remind.getType());
                        		rr.setOrderId(Long.valueOf(idArr[1]));
                        		list.add(rr);
							}
						}
                    }
                }
            }
        }else {
        	 list.add(remind);
		}
        if (!CollectionUtils.isEmpty(list)) {
        	 remindMapper.save(list);
		}
    }
    
    public Integer statistics(Remind remind){
    	return remindMapper.statistics(remind);
    }

	public void update(Remind remind) {
		remindMapper.update(remind);
	}


    public void saveOne(Remind remind) {
        remindMapper.saveOne(remind);
    }
}
