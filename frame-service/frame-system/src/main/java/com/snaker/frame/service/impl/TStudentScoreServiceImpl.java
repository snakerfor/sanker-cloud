package com.snaker.frame.service.impl;

import java.util.List;
import com.snaker.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.snaker.frame.mapper.TStudentScoreMapper;
import com.snaker.frame.domain.TStudentScore;
import com.snaker.frame.service.ITStudentScoreService;
import com.snaker.common.core.text.Convert;

/**
 * 学生成绩Service业务层处理
 * 
 * @author snaker
 * @date 2021-11-09
 */
@Service
public class TStudentScoreServiceImpl implements ITStudentScoreService 
{
    @Autowired
    private TStudentScoreMapper tStudentScoreMapper;

    /**
     * 查询学生成绩
     * 
     * @param id 学生成绩ID
     * @return 学生成绩
     */
    @Override
    public TStudentScore selectTStudentScoreById(String id)
    {
        return tStudentScoreMapper.selectTStudentScoreById(id);
    }

    /**
     * 查询学生成绩列表
     * 
     * @param tStudentScore 学生成绩
     * @return 学生成绩
     */
    @Override
    public List<TStudentScore> selectTStudentScoreList(TStudentScore tStudentScore)
    {
        return tStudentScoreMapper.selectTStudentScoreList(tStudentScore);
    }

    /**
     * 新增学生成绩
     * 
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    @Override
    public int insertTStudentScore(TStudentScore tStudentScore)
    {
        tStudentScore.setCreateTime(DateUtils.getNowDate());
        return tStudentScoreMapper.insertTStudentScore(tStudentScore);
    }

    /**
     * 修改学生成绩
     * 
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    @Override
    public int updateTStudentScore(TStudentScore tStudentScore)
    {
        tStudentScore.setUpdateTime(DateUtils.getNowDate());
        return tStudentScoreMapper.updateTStudentScore(tStudentScore);
    }

    /**
     * 修改学生成绩(用户表单提交)
     *
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    @Override
    public int updateTStudentScoreForForm(TStudentScore tStudentScore)
    {
        tStudentScore.setUpdateTime(DateUtils.getNowDate());
        return tStudentScoreMapper.updateTStudentScoreForForm(tStudentScore);
    }

    /**
     * 删除学生成绩对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTStudentScoreByIds(String ids)
    {
        return tStudentScoreMapper.deleteTStudentScoreByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除学生成绩信息
     * 
     * @param id 学生成绩ID
     * @return 结果
     */
    public int deleteTStudentScoreById(String id)
    {
        return tStudentScoreMapper.deleteTStudentScoreById(id);
    }

    /**
     * 批量新增学生成绩
     *
     * @param tStudentScores 学生成绩集合
     * @return 结果
     */
    @Override
    public int insertTStudentScoreList(List<TStudentScore> tStudentScores)
    {
        return tStudentScoreMapper.insertTStudentScoreList(tStudentScores);
    }

    /**
     * 验证数据是否违反唯一性
     *
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    public boolean checkConstrain(TStudentScore tStudentScore){
        TStudentScore dto = new TStudentScore();
        dto.setStudentNo(tStudentScore.getStudentNo());
        dto.setSubjectCode(tStudentScore.getSubjectCode());
        if (tStudentScoreMapper.selectTStudentScoreList(dto).size() > 0) {
            return false;
        }
        return true;
    }

}
