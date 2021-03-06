package com.study.cancer.dao;

import com.study.cancer.model.Apply;
import com.study.cancer.model.ApplyListVo;

import java.util.List;
import java.util.Map;

public interface ApplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    int insert(Apply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    int insertSelective(Apply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    Apply selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    int updateByPrimaryKeySelective(Apply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    int updateByPrimaryKey(Apply record);

    List<Apply> selectByPatientId(Integer patientId);

    List<ApplyListVo> selectList(Map<Object, Object> map);

    List<Apply> selectByLimitAndWaitLevel(Map map);

    List<ApplyListVo> selectWaitDay(Map map);

    List<Integer> selectNumOfMonth(Map map);

    List<ApplyListVo> selectNumOfSeverity(Map map);
}