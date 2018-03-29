package com.study.cancer.service;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Apply;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.User;

import java.util.Date;
import java.util.Map;

public interface ApplyService {
    CommonResult addApply(Apply apply);

    PageInfo getSelfList(int page, int rows, Integer patientId, String applyStartDate, String applyEndDate, String state);

    PageInfo getList(int page, int rows, Integer patientId, String applyStartDate, String applyEndDate, String state, String patientName, Integer medicalRecordNo);

    CommonResult getApplyById(Integer applyId);

    CommonResult getUserByAuthorization(String authorization);
}
