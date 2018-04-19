package com.dhxx.web;

import com.dhxx.common.util.DateEditorUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p> 类说明 </p>
 * @author jhy
 * Date: 2017年3月22日
 * @version 1.01
 *
 */
public class BaseController {

    //绑定date格式
    @InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new DateEditorUtils());
    }
    
}
