package com.dhxx.service.mapper.transport.routedeviate;

import com.dhxx.facade.entity.transport.routedeviate.TransportRouteDeviate;

import java.util.List;

public interface TransportRouteDeviateMapper {

    void  add(TransportRouteDeviate transportRouteDeviate);

    List<TransportRouteDeviate> find(TransportRouteDeviate transportRouteDeviate);
}
