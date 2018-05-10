package com.study.cancer.service;

import com.study.cancer.model.Bed;
import com.study.cancer.model.CommonResult;

public interface BedService {
    CommonResult addBed(Bed bed);

    CommonResult showBedList(int page, int rows, String bedId, String state, String patientId, String bedNo);

    CommonResult modifyBed(Bed bed);
}
