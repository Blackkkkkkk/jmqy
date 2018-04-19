package com.dhxx.service.biz.charter.complaint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.service.mapper.charter.complaint.ComplaintMapper;

import java.util.List;

/**
 * <p> 投诉表 </p>
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 *
 */
@Service
@Transactional
public class ComplaintBiz {

    private static Logger log = LoggerFactory.getLogger(ComplaintBiz.class);

	@Autowired
	private ComplaintMapper complaintMapper;
	
	//保存
	public void save(Complaint complaint){
		log.info("ComplaintBiz.save()");
		complaintMapper.save(complaint);
	}

    public PageInfo<Complaint> list(Complaint complaint) {
        PageHelper.startPage(complaint.getPageNum(), complaint.getPageSize());
        List<Complaint> list = complaintMapper.list(complaint);
        PageInfo<Complaint> pageInfo = new PageInfo<Complaint>(list);
        return pageInfo;
    }

	public List<Complaint> find(Complaint complaint) {
		return complaintMapper.list(complaint);
	}

	public void update(Complaint complaint) {
		complaintMapper.update(complaint);
	}

}
