package com.study.cancer.model;

import java.io.Serializable;

public class WaitLevelConfig implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wait_level_config.id
     *
     * @mbggenerated Fri Apr 27 17:04:17 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wait_level_config.wait_time_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    private String waitTimeWeight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wait_level_config.severity_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    private String severityWeight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wait_level_config.break_appointment_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    private String breakAppointmentWeight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wait_level_config
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wait_level_config.id
     *
     * @return the value of wait_level_config.id
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wait_level_config.id
     *
     * @param id the value for wait_level_config.id
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wait_level_config.wait_time_weight
     *
     * @return the value of wait_level_config.wait_time_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public String getWaitTimeWeight() {
        return waitTimeWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wait_level_config.wait_time_weight
     *
     * @param waitTimeWeight the value for wait_level_config.wait_time_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public void setWaitTimeWeight(String waitTimeWeight) {
        this.waitTimeWeight = waitTimeWeight == null ? null : waitTimeWeight.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wait_level_config.severity_weight
     *
     * @return the value of wait_level_config.severity_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public String getSeverityWeight() {
        return severityWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wait_level_config.severity_weight
     *
     * @param severityWeight the value for wait_level_config.severity_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public void setSeverityWeight(String severityWeight) {
        this.severityWeight = severityWeight == null ? null : severityWeight.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wait_level_config.break_appointment_weight
     *
     * @return the value of wait_level_config.break_appointment_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public String getBreakAppointmentWeight() {
        return breakAppointmentWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wait_level_config.break_appointment_weight
     *
     * @param breakAppointmentWeight the value for wait_level_config.break_appointment_weight
     *
     * @mbggenerated Fri Apr 27 17:04:18 CST 2018
     */
    public void setBreakAppointmentWeight(String breakAppointmentWeight) {
        this.breakAppointmentWeight = breakAppointmentWeight == null ? null : breakAppointmentWeight.trim();
    }
}