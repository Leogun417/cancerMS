package com.study.cancer.dao;

import com.study.cancer.model.leaveData;

public interface leaveDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table leave_data
     *
     * @mbggenerated Fri Apr 27 17:06:16 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table leave_data
     *
     * @mbggenerated Fri Apr 27 17:06:16 CST 2018
     */
    int insert(leaveData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table leave_data
     *
     * @mbggenerated Fri Apr 27 17:06:16 CST 2018
     */
    int insertSelective(leaveData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table leave_data
     *
     * @mbggenerated Fri Apr 27 17:06:16 CST 2018
     */
    leaveData selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table leave_data
     *
     * @mbggenerated Fri Apr 27 17:06:16 CST 2018
     */
    int updateByPrimaryKeySelective(leaveData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table leave_data
     *
     * @mbggenerated Fri Apr 27 17:06:16 CST 2018
     */
    int updateByPrimaryKey(leaveData record);
}