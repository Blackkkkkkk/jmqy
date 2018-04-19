package com.dhxx.web.tackle;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.charter.complaint.Complaint;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.service.charter.complaint.ComplaintFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jhy
 * @date 2017/9/18
 * @description
 * 投诉处理
 */
@Controller
@RequestMapping("tackle")
public class TackleController {

    @Reference(protocol="dubbo")
    private ComplaintFacade complaintFacade;

    @Reference(protocol="dubbo")
    private RemindFacade remindFacade;

    @RequestMapping(value = "list")
    public String list(Complaint complaint, Model model, HttpServletRequest requst) {
        PageInfo<Complaint> complaints = (PageInfo<Complaint>) complaintFacade.list(complaint);
        model.addAttribute("complaint",complaint);
        model.addAttribute("complaints", complaints);

        return "tackle/list";
    }

    @ResponseBody
    @RequestMapping(value="remind")
    public Map<String, Object> remind(Remind remind) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            remindFacade.save(remind);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

}
