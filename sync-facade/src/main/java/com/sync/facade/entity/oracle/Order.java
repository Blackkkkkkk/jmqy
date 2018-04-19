package com.sync.facade.entity.oracle;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Order  implements Serializable {
    private  String ids ; // 查询订单表的id组

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<String> getIdList() {
        if(StringUtils.isEmpty(ids))
            return null;
        return Arrays.asList(ids.split(","));
    }
}
