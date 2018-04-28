package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.TreatmentProcessMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.TreatmentProcess;
import com.study.cancer.service.TreatmentProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TreatmentProcessServiceImpl implements TreatmentProcessService {

    @Resource
    TreatmentProcessMapper treatmentProcessMapper;

    @Override
    public CommonResult addProcess(TreatmentProcess treatmentProcess) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = treatmentProcessMapper.insert(treatmentProcess);
        if (insert > 0) {
            result.setSuccess(true);
            result.setData(treatmentProcess);
            result.setMessage("过程建立成功");
        } else {
            result.setMessage("过程建立失败");
        }
        return result;
    }

    @Override
    public CommonResult showProcessList(int page, int rows, String medicalRecordNo) {
        PageHelper.startPage(page, rows);
        CommonResult<Object> result = new CommonResult<>();
        List<TreatmentProcess> processList = treatmentProcessMapper.selectByMedicalRecordNo(medicalRecordNo);
        PageInfo<TreatmentProcess> processPageInfo = new PageInfo<>(processList);
        if (processList != null && processList.size() > 0) {
            result.setMessage("获取就诊过程成功");
            result.setSuccess(true);
        } else {
            result.setMessage("获取就诊过程失败");
        }
        result.setData(processPageInfo);
        return result;
    }
}
