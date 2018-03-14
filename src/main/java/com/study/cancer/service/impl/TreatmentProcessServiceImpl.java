package com.study.cancer.service.impl;

import com.study.cancer.dao.TreatmentProcessMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.TreatmentProcess;
import com.study.cancer.service.TreatmentProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
