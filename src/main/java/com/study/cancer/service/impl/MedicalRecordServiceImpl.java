package com.study.cancer.service.impl;

import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.dao.MedicalRecordMapper;
import com.study.cancer.model.Apply;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Resource
    MedicalRecordMapper medicalRecordMapper;
    @Override
    public CommonResult addRecord(MedicalRecord medicalRecord) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = medicalRecordMapper.insert(medicalRecord);
        if (insert > 0) {
            result.setSuccess(true);
            result.setData(medicalRecord.getId());
            result.setMessage("就诊记录创建成功");
        } else {
            result.setMessage("就诊记录创建失败");
        }
        return result;
    }
}
