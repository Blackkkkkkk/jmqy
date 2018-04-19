package com.dhxx.service.biz.transport.routedeviate;


import com.dhxx.facade.entity.transport.routedeviate.TransportRouteDeviate;
import com.dhxx.service.mapper.transport.routedeviate.TransportRouteDeviateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransportRouteDeviateBiz {
    @Autowired
    TransportRouteDeviateMapper transportRouteDeviateMapper;

    public  void add(TransportRouteDeviate transportRouteDeviate){ transportRouteDeviateMapper.add(transportRouteDeviate);}


    public List<TransportRouteDeviate>  find(TransportRouteDeviate transportRouteDeviate){return  transportRouteDeviateMapper.find(transportRouteDeviate);}
}
