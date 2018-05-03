package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.dao.BedMapper;
import com.study.cancer.dao.MedicalRecordMapper;
import com.study.cancer.model.*;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Resource
    MedicalRecordMapper medicalRecordMapper;

    @Resource
    ApplyMapper applyMapper;

    @Resource
    BedMapper bedMapper;

    @Override
    public CommonResult resetBed(String patientId) {
        CommonResult<Object> result = new CommonResult<>();
        Bed bed = bedMapper.selectByPatient(patientId);
        if (bed != null) {
            bed.setPatientId("");
            bed.setState("0");
            int update = bedMapper.updateByPrimaryKeySelective(bed);
            if (update > 0) {
                result.setSuccess(true);
                result.setMessage("重置床位成功");
            }
        } else {
            result.setMessage("没有找到床位");
        }
        return result;
    }

    @Override
    public CommonResult editRecord(MedicalRecord medicalRecord) {
        CommonResult<Object> result = new CommonResult<>();
        int update = medicalRecordMapper.updateByPrimaryKeySelective(medicalRecord);
        if (update > 0) {
            result.setSuccess(true);
            result.setMessage("更新记录成功");
        } else {
            result.setMessage("更新记录失败");
        }
        return result;
    }

    @Override
    public CommonResult getRecord(String medicalRecordNo) {
        CommonResult<Object> result = new CommonResult<>();
        MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(Integer.parseInt(medicalRecordNo));
        if (medicalRecord != null) {
            result.setSuccess(true);
            result.setMessage("获取就诊记录成功");
            result.setData(medicalRecord);
        } else {
            result.setMessage("获取就诊记录失败");
        }
        return result;
    }

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
                apply.setState(ApplyStateConstant.LINE_UP);//申请单状态为正在排队
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

    @Override
    public CommonResult showRecordList(int page, int rows, String patientName, String phoneNumber, String medicalRecordNo, String medicalGroup, String state) {
        PageHelper.startPage(page, rows);
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("patientName", patientName);
        map.put("phoneNumber", phoneNumber);
        map.put("medicalRecordNo", medicalRecordNo);
        map.put("medicalGroup", medicalGroup);
        map.put("state", state);
        List<MedicalRecordListVo> medicalRecordListVos = medicalRecordMapper.selectList(map);
        PageInfo<MedicalRecordListVo> medicalRecordListVoPageInfo = new PageInfo<>(medicalRecordListVos);
        if (medicalRecordListVos != null && medicalRecordListVos.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取就诊记录列表成功");
        } else {
            result.setMessage("获取就诊记录列表失败");
        }
        result.setData(medicalRecordListVoPageInfo);
        return result;
    }
}
