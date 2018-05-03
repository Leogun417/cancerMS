package com.study.cancer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class MedicalRecord implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.id
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.patient_id
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private Integer patientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.first_in_hospital_date
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date firstInHospitalDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.severity
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private String severity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.is_in_hospital
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private String isInHospital;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.next_to_hospital_date
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date nextToHospitalDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.treatment_plan
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private String treatmentPlan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.treatment_times
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private Integer treatmentTimes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column medical_record.to_hospital_times
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private Integer toHospitalTimes;

    /**
     * 就诊过程中0 就诊结束1
     */
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table medical_record
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.id
     *
     * @return the value of medical_record.id
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.id
     *
     * @param id the value for medical_record.id
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.patient_id
     *
     * @return the value of medical_record.patient_id
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.patient_id
     *
     * @param patientId the value for medical_record.patient_id
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.first_in_hospital_date
     *
     * @return the value of medical_record.first_in_hospital_date
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public Date getFirstInHospitalDate() {
        return firstInHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.first_in_hospital_date
     *
     * @param firstInHospitalDate the value for medical_record.first_in_hospital_date
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setFirstInHospitalDate(Date firstInHospitalDate) {
        this.firstInHospitalDate = firstInHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.severity
     *
     * @return the value of medical_record.severity
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.severity
     *
     * @param severity the value for medical_record.severity
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setSeverity(String severity) {
        this.severity = severity == null ? null : severity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.is_in_hospital
     *
     * @return the value of medical_record.is_in_hospital
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public String getIsInHospital() {
        return isInHospital;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.is_in_hospital
     *
     * @param isInHospital the value for medical_record.is_in_hospital
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setIsInHospital(String isInHospital) {
        this.isInHospital = isInHospital == null ? null : isInHospital.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.next_to_hospital_date
     *
     * @return the value of medical_record.next_to_hospital_date
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public Date getNextToHospitalDate() {
        return nextToHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.next_to_hospital_date
     *
     * @param nextToHospitalDate the value for medical_record.next_to_hospital_date
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setNextToHospitalDate(Date nextToHospitalDate) {
        this.nextToHospitalDate = nextToHospitalDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.treatment_plan
     *
     * @return the value of medical_record.treatment_plan
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.treatment_plan
     *
     * @param treatmentPlan the value for medical_record.treatment_plan
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan == null ? null : treatmentPlan.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.treatment_times
     *
     * @return the value of medical_record.treatment_times
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public Integer getTreatmentTimes() {
        return treatmentTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.treatment_times
     *
     * @param treatmentTimes the value for medical_record.treatment_times
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setTreatmentTimes(Integer treatmentTimes) {
        this.treatmentTimes = treatmentTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column medical_record.to_hospital_times
     *
     * @return the value of medical_record.to_hospital_times
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public Integer getToHospitalTimes() {
        return toHospitalTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column medical_record.to_hospital_times
     *
     * @param toHospitalTimes the value for medical_record.to_hospital_times
     *
     * @mbggenerated Fri Apr 27 14:46:40 CST 2018
     */
    public void setToHospitalTimes(Integer toHospitalTimes) {
        this.toHospitalTimes = toHospitalTimes;
    }
}