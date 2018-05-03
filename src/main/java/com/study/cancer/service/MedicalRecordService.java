package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.LeaveData;
import com.study.cancer.model.LeaveRecord;
import com.study.cancer.model.MedicalRecord;

public interface MedicalRecordService {
    CommonResult resetBed(String patientId);
    CommonResult editRecord(MedicalRecord medicalRecord);
    CommonResult getRecord(String medicalRecordNo);
    CommonResult addRecord(MedicalRecord medicalRecord);
    CommonResult jugeSeverity(String medicalRecordNo, String level, String applyId);
    CommonResult showRecordList(int page, int rows, String patientName, String phoneNumber, String medicalRecordNo, String medicalGroup, String state);
}
