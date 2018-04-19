package com.dhxx.service.biz.score;

import com.dhxx.facade.entity.score.Score;
import com.dhxx.service.mapper.score.ScoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jhy
 * @date 2017/9/17
 * @description
 */
@Service
@Transactional
public class ScoreBiz {

    @Autowired
    private ScoreMapper scoreMapper;

    public PageInfo<Score> list(Score score) {
        PageHelper.startPage(score.getPageNum(), score.getPageSize());
        List<Score> list = scoreMapper.list(score);
        PageInfo<Score> pageInfo = new PageInfo<Score>(list);
        return pageInfo;
    }

    public void save(Score score) {
        scoreMapper.save(score);
    }
}
