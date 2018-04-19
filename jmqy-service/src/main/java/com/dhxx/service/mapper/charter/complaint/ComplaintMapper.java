package com.dhxx.service.mapper.charter.complaint;

import com.dhxx.facade.entity.charter.complaint.Complaint;

import java.util.List;

public interface ComplaintMapper {

	void save(Complaint complaint);

    List<Complaint> list(Complaint complaint);

	void update(Complaint complaint);
}
