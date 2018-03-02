package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;

public interface MedicalRecordService {
    CommonResult addRecord(MedicalRecord medicalRecord);
}
