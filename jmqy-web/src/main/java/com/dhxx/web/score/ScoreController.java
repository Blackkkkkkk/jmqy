package com.dhxx.web.score;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.score.Score;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.score.ScoreFacade;
import com.dhxx.web.BaseController;
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
 * @date 2017/9/17
 * @description
 */
@Controller
@RequestMapping("score")
public class ScoreController extends BaseController {

    @Reference(protocol="dubbo")
    private ScoreFacade scoreFacade;

    @Reference(protocol="dubbo")
    private RemindFacade remindFacade;

    @RequestMapping(value = "userList")
    public String userList(Score score, Model model, HttpServletRequest requst) {
        PageInfo<Score> scores = (PageInfo<Score>) scoreFacade.list(score);
        model.addAttribute("score",score);
        model.addAttribute("scores", scores);
        return "score/userList";
    }

    @RequestMapping(value = "setScore")
    public String setScore(Score score, Model model, HttpServletRequest requst) {
        model.addAttribute("score",score);
        return "score/setScore";
    }

    @ResponseBody
    @RequestMapping(value="doSetScore")
    public Map<String, Object> doSetScore(Score score) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            scoreFacade.save(score);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
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
