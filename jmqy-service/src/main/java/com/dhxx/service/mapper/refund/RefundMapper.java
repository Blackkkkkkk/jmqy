package com.dhxx.service.mapper.refund;

import com.dhxx.facade.entity.refund.Refund;

import java.util.List;

public interface RefundMapper {

    void update(Refund refund);

    void save(Refund refund);

    List<Refund> list(Refund order);
}
