package com.study.cancer.model;

import java.io.Serializable;
import java.util.Date;

public class TreatmentProcess implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column treatment_process.id
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column treatment_process.medical_record_no
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private String medicalRecordNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column treatment_process.patient_action
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private String patientAction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column treatment_process.doctor_action
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private String doctorAction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column treatment_process.create_date
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column treatment_process.doctor_name
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private String doctorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table treatment_process
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column treatment_process.id
     *
     * @return the value of treatment_process.id
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column treatment_process.id
     *
     * @param id the value for treatment_process.id
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column treatment_process.medical_record_no
     *
     * @return the value of treatment_process.medical_record_no
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public String getMedicalRecordNo() {
        return medicalRecordNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column treatment_process.medical_record_no
     *
     * @param medicalRecordNo the value for treatment_process.medical_record_no
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public void setMedicalRecordNo(String medicalRecordNo) {
        this.medicalRecordNo = medicalRecordNo == null ? null : medicalRecordNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column treatment_process.patient_action
     *
     * @return the value of treatment_process.patient_action
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public String getPatientAction() {
        return patientAction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column treatment_process.patient_action
     *
     * @param patientAction the value for treatment_process.patient_action
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public void setPatientAction(String patientAction) {
        this.patientAction = patientAction == null ? null : patientAction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column treatment_process.doctor_action
     *
     * @return the value of treatment_process.doctor_action
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public String getDoctorAction() {
        return doctorAction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column treatment_process.doctor_action
     *
     * @param doctorAction the value for treatment_process.doctor_action
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public void setDoctorAction(String doctorAction) {
        this.doctorAction = doctorAction == null ? null : doctorAction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column treatment_process.create_date
     *
     * @return the value of treatment_process.create_date
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column treatment_process.create_date
     *
     * @param createDate the value for treatment_process.create_date
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column treatment_process.doctor_name
     *
     * @return the value of treatment_process.doctor_name
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column treatment_process.doctor_name
     *
     * @param doctorName the value for treatment_process.doctor_name
     *
     * @mbggenerated Fri Apr 27 22:24:14 CST 2018
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }
}