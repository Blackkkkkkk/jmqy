package com.dhxx.service.mapper.score;

import com.dhxx.facade.entity.score.Score;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
public interface ScoreMapper {

    List<Score> list(Score score);

    void save(Score score);

}
