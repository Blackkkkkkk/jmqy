package com.dhxx.facade.service.transport.routedeviate;

import com.dhxx.facade.entity.transport.routedeviate.TransportRouteDeviate;

import java.util.List;

public interface TransportRouteDeviateFacade {
    void  add(TransportRouteDeviate transportRouteDeviate);

    List<TransportRouteDeviate> find(TransportRouteDeviate transportRouteDeviate);
}
