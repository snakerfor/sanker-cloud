package com.snaker.frame.mapper;

import com.snaker.frame.domain.TStudentScore;
import java.util.List;

/**
 * 学生成绩Mapper接口
 * 
 * @author snaker
 * @date 2021-11-09
 */
public interface TStudentScoreMapper 
{
    /**
     * 查询学生成绩
     * 
     * @param id 学生成绩ID
     * @return 学生成绩
     */
    public TStudentScore selectTStudentScoreById(String id);

    /**
     * 查询学生成绩列表
     * 
     * @param tStudentScore 学生成绩
     * @return 学生成绩集合
     */
    public List<TStudentScore> selectTStudentScoreList(TStudentScore tStudentScore);

    /**
     * 新增学生成绩
     * 
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    public int insertTStudentScore(TStudentScore tStudentScore);

    /**
     * 修改学生成绩
     * 
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    public int updateTStudentScore(TStudentScore tStudentScore);

    /**
     * 修改学生成绩 (用户表单提交)
     *
     * @param tStudentScore 学生成绩
     * @return 结果
     */
    public int updateTStudentScoreForForm(TStudentScore tStudentScore);

    /**
     * 删除学生成绩
     * 
     * @param id 学生成绩ID
     * @return 结果
     */
    public int deleteTStudentScoreById(String id);

    /**
     * 批量删除学生成绩
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStudentScoreByIds(String[] ids);

    /**
     * 批量新增学生成绩
     *
     * @param tStudentScores 学生成绩集合
     * @return 结果
     */
    public int insertTStudentScoreList(List<TStudentScore> tStudentScores);
}
