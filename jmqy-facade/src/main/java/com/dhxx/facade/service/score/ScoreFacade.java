package com.dhxx.facade.service.score;

import com.dhxx.facade.entity.score.Score;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
public interface ScoreFacade {

    Object list(Score score);

    void save(Score score);

}


