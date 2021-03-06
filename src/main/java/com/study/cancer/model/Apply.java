package com.study.cancer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Apply implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.id
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.medical_record_no
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private Integer medicalRecordNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.patient_id
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private Integer patientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.apply_date
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.state
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.to_hospital_date
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date toHospitalDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.wait_level
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private Integer waitLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.leucocyte_concentration
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private String leucocyteConcentration;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.neutrophil_concentration
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private String neutrophilConcentration;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.remark
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.is_visible
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private String isVisible;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apply.is_again
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private String isAgain;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table apply
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.id
     *
     * @return the value of apply.id
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.id
     *
     * @param id the value for apply.id
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.medical_record_no
     *
     * @return the value of apply.medical_record_no
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public Integer getMedicalRecordNo() {
        return medicalRecordNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.medical_record_no
     *
     * @param medicalRecordNo the value for apply.medical_record_no
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setMedicalRecordNo(Integer medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.patient_id
     *
     * @return the value of apply.patient_id
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.patient_id
     *
     * @param patientId the value for apply.patient_id
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.apply_date
     *
     * @return the value of apply.apply_date
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.apply_date
     *
     * @param applyDate the value for apply.apply_date
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.state
     *
     * @return the value of apply.state
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.state
     *
     * @param state the value for apply.state
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.to_hospital_date
     *
     * @return the value of apply.to_hospital_date
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public Date getToHospitalDate() {
        return toHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.to_hospital_date
     *
     * @param toHospitalDate the value for apply.to_hospital_date
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setToHospitalDate(Date toHospitalDate) {
        this.toHospitalDate = toHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.wait_level
     *
     * @return the value of apply.wait_level
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public Integer getWaitLevel() {
        return waitLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.wait_level
     *
     * @param waitLevel the value for apply.wait_level
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setWaitLevel(Integer waitLevel) {
        this.waitLevel = waitLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.leucocyte_concentration
     *
     * @return the value of apply.leucocyte_concentration
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public String getLeucocyteConcentration() {
        return leucocyteConcentration;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.leucocyte_concentration
     *
     * @param leucocyteConcentration the value for apply.leucocyte_concentration
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setLeucocyteConcentration(String leucocyteConcentration) {
        this.leucocyteConcentration = leucocyteConcentration == null ? null : leucocyteConcentration.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.neutrophil_concentration
     *
     * @return the value of apply.neutrophil_concentration
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public String getNeutrophilConcentration() {
        return neutrophilConcentration;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.neutrophil_concentration
     *
     * @param neutrophilConcentration the value for apply.neutrophil_concentration
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setNeutrophilConcentration(String neutrophilConcentration) {
        this.neutrophilConcentration = neutrophilConcentration == null ? null : neutrophilConcentration.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.remark
     *
     * @return the value of apply.remark
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.remark
     *
     * @param remark the value for apply.remark
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.is_visible
     *
     * @return the value of apply.is_visible
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public String getIsVisible() {
        return isVisible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.is_visible
     *
     * @param isVisible the value for apply.is_visible
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible == null ? null : isVisible.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apply.is_again
     *
     * @return the value of apply.is_again
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public String getIsAgain() {
        return isAgain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apply.is_again
     *
     * @param isAgain the value for apply.is_again
     *
     * @mbggenerated Thu May 03 08:56:50 CST 2018
     */
    public void setIsAgain(String isAgain) {
        this.isAgain = isAgain == null ? null : isAgain.trim();
    }
}