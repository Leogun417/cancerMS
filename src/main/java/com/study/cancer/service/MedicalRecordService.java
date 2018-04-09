package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;

public interface MedicalRecordService {
    CommonResult addRecord(MedicalRecord medicalRecord);
    CommonResult jugeSeverity(String medicalRecordNo, String level, String applyId);
}
