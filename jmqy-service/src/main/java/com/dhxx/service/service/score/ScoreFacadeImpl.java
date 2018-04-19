package com.dhxx.service.service.score;

import com.alibaba.dubbo.config.annotation.Service;
import com.dhxx.facade.entity.score.Score;
import com.dhxx.facade.service.score.ScoreFacade;
import com.dhxx.service.biz.score.ScoreBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
@Service(protocol = {"dubbo"})
public class ScoreFacadeImpl implements ScoreFacade {

    @Autowired
    private ScoreBiz scoreBiz;

    @Override
    public Object list(Score score) {
        return scoreBiz.list(score);
    }

    @Override
    public void save(Score score) {
        scoreBiz.save(score);
    }
}
