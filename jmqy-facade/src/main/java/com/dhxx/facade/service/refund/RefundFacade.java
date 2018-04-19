package com.dhxx.facade.service.refund;

import com.dhxx.facade.entity.refund.Refund;

import java.util.List;

public interface RefundFacade {

    void save(Refund refund);

    void update(Refund refund);

    List<Refund> list(Refund refund);
}
