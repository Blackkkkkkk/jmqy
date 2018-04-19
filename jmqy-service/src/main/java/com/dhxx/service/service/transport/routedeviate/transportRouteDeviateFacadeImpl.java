package com.dhxx.service.service.transport.routedeviate;


import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.transport.routedeviate.TransportRouteDeviate;
import com.dhxx.facade.service.transport.routedeviate.TransportRouteDeviateFacade;
import com.dhxx.service.biz.transport.routedeviate.TransportRouteDeviateBiz;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(protocol = {"dubbo"})
public class transportRouteDeviateFacadeImpl implements TransportRouteDeviateFacade {

    @Autowired
    private TransportRouteDeviateBiz transportRouteDeviateBiz;

    public  void add(TransportRouteDeviate transportRouteDeviate){ transportRouteDeviateBiz.add(transportRouteDeviate);}

    public List<TransportRouteDeviate> find(TransportRouteDeviate transportRouteDeviate){return transportRouteDeviateBiz.find(transportRouteDeviate);}
}
