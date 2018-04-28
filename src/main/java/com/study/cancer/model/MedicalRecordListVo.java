package com.study.cancer.model;

public class MedicalRecordListVo extends MedicalRecord{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.sex
     *
     * @mbggenerated Mon Feb 26 14:28:10 CST 2018
     */
    private String sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.username
     *
     * @mbggenerated Mon Feb 26 14:28:10 CST 2018
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone_number
     *
     * @mbggenerated Mon Feb 26 14:28:11 CST 2018
     */
    private String phoneNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.age
     *
     * @mbggenerated Mon Feb 26 14:28:11 CST 2018
     */
    private Integer age;

    private String medicalGroup;

    public String getMedicalGroup() {
        return medicalGroup;
    }

    public void setMedicalGroup(String medicalGroup) {
        this.medicalGroup = medicalGroup;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
