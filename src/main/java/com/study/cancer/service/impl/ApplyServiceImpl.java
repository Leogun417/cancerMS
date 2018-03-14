package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.dao.TreatmentProcessMapper;
import com.study.cancer.model.Apply;
import com.study.cancer.model.ApplyListVo;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.TreatmentProcess;
import com.study.cancer.service.ApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    ApplyMapper applyMapper;

    @Resource
    TreatmentProcessMapper treatmentProcessMapper;

    @Override
    public CommonResult addApply(Apply apply) {
        CommonResult<Object> result = new CommonResult<>();
        HashMap<Object, Object> resulDatatMap = new HashMap<>();
        List<Apply> oldApplys = applyMapper.selectByPatientId(apply.getPatientId());
        if (oldApplys != null && oldApplys.size() > 0) {//0正在排队 1排队完成 2入院 3爽约
            for (Apply oldApply : oldApplys) {
                if (oldApply.getState().equals("0")) {
                    result.setMessage("申请正在排队，请勿重复提交");
                    break;
                }
            }

        } else {
            int insert = applyMapper.insertSelective(apply);
            if (insert > 0) {
                TreatmentProcess treatmentProcess = new TreatmentProcess();
                treatmentProcess.setCreateDate(apply.getApplyDate());
                treatmentProcess.setMedicalRecordNo(apply.getMedicalRecordNo()+"");
                treatmentProcess.setPatientAction("提出入院申请");
                int insertTreatmentProcess = treatmentProcessMapper.insertSelective(treatmentProcess);
                result.setSuccess(true);
                resulDatatMap.put("applyId", apply.getId());
                if (insertTreatmentProcess <= 0) {
                    result.setMessage("申请提交成功,但未成功建立过程");
                    return result;
                }
                resulDatatMap.put("treatmentProcessId", treatmentProcess.getId());
                result.setData(resulDatatMap);
                result.setMessage("申请提交成功");
            } else {
                result.setMessage("申请提交失败");
            }
        }
        return result;
    }

    @Override
    public PageInfo getSelfList(int page, int rows, Integer patientId, String applyStartDate, String applyEndDate, String state) {
        PageHelper.startPage(page, rows);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("patientId", patientId);
        map.put("applyStartDate", applyStartDate);
        map.put("applyEndDate", applyEndDate);
        map.put("state", state);
        List<ApplyListVo> applyList = applyMapper.selectList(map);
        return new PageInfo(applyList);
    }

    @Override
    public PageInfo getList(int page, int rows, Integer patientId, String applyStartDate, String applyEndDate, String state, String patientName, Integer medicalRecordNo) {
        PageHelper.startPage(page, rows);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("medicalRecordNo", medicalRecordNo);
        map.put("username", patientName);
        map.put("patientId", patientId);
        map.put("applyStartDate", applyStartDate);
        map.put("applyEndDate", applyEndDate);
        map.put("state", state);
        List<ApplyListVo> applyList = applyMapper.selectList(map);
        return new PageInfo(applyList);
    }

    @Override
    public CommonResult getApplyById(Integer applyId) {
        HashMap<Object, Object> map = new HashMap<>();
        CommonResult<Object> result = new CommonResult<>();
        map.put("applyId", applyId);
        List<ApplyListVo> applyListVos = applyMapper.selectList(map);
        if (applyListVos != null && applyListVos.size() > 0) {
            result.setData(applyListVos.get(0));
            result.setSuccess(true);
        } else {
            result.setMessage("申请表获取失败");
        }
        return result;
    }
}
