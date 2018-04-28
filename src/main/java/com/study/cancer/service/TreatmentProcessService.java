package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.TreatmentProcess;

public interface TreatmentProcessService {
    CommonResult addProcess(TreatmentProcess treatmentProcess);

    CommonResult showProcessList(int page, int rows, String medicalRecordNo);
}
