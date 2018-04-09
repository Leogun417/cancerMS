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

    @Resource
    ApplyMapper applyMapper;
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

    @Override
    public CommonResult jugeSeverity(String medicalRecordNo, String level, String applyId) {
        CommonResult<Object> result = new CommonResult<>();
        MedicalRecord oldMedicalRecord = medicalRecordMapper.selectByPrimaryKey(Integer.parseInt(medicalRecordNo));
        if (oldMedicalRecord !=null) {
            oldMedicalRecord.setSeverity(level);
            int update = medicalRecordMapper.updateByPrimaryKeySelective(oldMedicalRecord);
            if (update > 0) {
                Apply apply = new Apply();
                apply.setId(Integer.parseInt(applyId));
                apply.setState("1");//申请单状态为正在排队
                applyMapper.updateByPrimaryKeySelective(apply);
                result.setSuccess(true);
                result.setMessage("危重度评定成功");
                result.setData(oldMedicalRecord);
            } else {
                result.setMessage("危重度评定失败");
            }
        } else {
            result.setMessage("获取旧记录失败");
        }
        return result;
    }
}
