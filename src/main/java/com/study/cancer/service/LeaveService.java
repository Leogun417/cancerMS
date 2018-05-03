package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.LeaveData;
import com.study.cancer.model.LeaveRecord;

public interface LeaveService {
    CommonResult addLeaveRecord(LeaveRecord leaveRecord);
    CommonResult addLeaveData(LeaveData leaveData);
    CommonResult checkData();
    CommonResult getLeaveDataConfig();
}
