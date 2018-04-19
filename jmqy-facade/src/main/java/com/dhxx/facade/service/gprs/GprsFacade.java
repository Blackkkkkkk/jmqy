package com.dhxx.facade.service.gprs;





import com.dhxx.facade.entity.gprs.GprsCar;

import java.util.List;
import java.util.Map;

public interface GprsFacade {

    List<Map<String,Object>> find(GprsCar gprsCar);

    void  save(GprsCar gprsCar);

    void  update(GprsCar gprsCar);

}
