package com.dhxx.facade.service.rule;





import com.dhxx.facade.entity.rule.RouteDeviate;

import java.util.List;
import java.util.Map;

public interface RouteDeviateFacade {

    void update(RouteDeviate routeDeviate);

    Map<String,Object> find(RouteDeviate routeDeviate);
}
