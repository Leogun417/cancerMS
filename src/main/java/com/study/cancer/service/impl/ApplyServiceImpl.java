package com.study.cancer.service.impl;

import cn.com.boco.App;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.*;
import com.study.cancer.model.*;
import com.study.cancer.service.ApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    ApplyMapper applyMapper;

    @Resource
    BedMapper bedMapper;

    @Resource
    TreatmentProcessMapper treatmentProcessMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    WaitLevelConfigMapper waitLevelConfigMapper;

    @Override
    public CommonResult addApply(Apply apply) {
        CommonResult<Object> result = new CommonResult<>();
        HashMap<Object, Object> resulDatatMap = new HashMap<>();
        List<Apply> oldApplys = applyMapper.selectByPatientId(apply.getPatientId());
        if (oldApplys != null && oldApplys.size() > 0) {//0等待处理 1正在排队 2排队完成 3入院 4爽约
            for (Apply oldApply : oldApplys) {
                if (!(oldApply.getState().equals("3") || oldApply.getState().equals("4"))) {
                    result.setMessage("申请已提交过，请勿重复提交");
                    return result;
                }
            }

        }

        int insert = applyMapper.insertSelective(apply);
        if (insert > 0) {
            TreatmentProcess treatmentProcess = new TreatmentProcess();
            treatmentProcess.setCreateDate(apply.getApplyDate());
            treatmentProcess.setMedicalRecordNo(apply.getMedicalRecordNo() + "");
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

    @Override
    public CommonResult getUserByAuthorization(String authorization) {
        CommonResult<Object> result = new CommonResult<>();
        List<User> users = userMapper.selectByAthorization(authorization);
        if (users != null && users.size() > 0) {
            result.setSuccess(true);
            result.setData(users);
        } else {
            result.setMessage("获取该相应权限的用户失败");
        }
        return result;
    }

    @Override
    public CommonResult arrange() {
        CommonResult<Object> result = new CommonResult<>();
        List<Bed> unusedBeds = bedMapper.selectNumByState("0");
        if (unusedBeds != null && unusedBeds.size() > 0) {
            WaitLevelConfig waitLevelConfig = waitLevelConfigMapper.selectByPrimaryKey(1);
            Map map = new HashMap();
            map.put("limit", unusedBeds.size());
            map.put("severityWeight", waitLevelConfig.getSeverityWeight());
            map.put("waitTimeWeight", waitLevelConfig.getWaitTimeWeight());
            map.put("breakAppointmentWeight", waitLevelConfig.getBreakAppointmentWeight());
            List<Apply> applies = applyMapper.selectByLimitAndWaitLevel(map);
            if (applies != null && applies.size() > 0) {
                for (Apply apply : applies) {
                    apply.setState(ApplyStateConstant.FINISH_LINE_UP);//排队完成
                    Calendar calendar = Calendar.getInstance();
                    Date now = new Date();
                    calendar.setTime(now);
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    int amOrpm = gregorianCalendar.get(GregorianCalendar.AM_PM);
                    if (amOrpm == 0) {
                        calendar.add(Calendar.HOUR_OF_DAY, +5);//如果是上午，就五个小时内入院
                        apply.setToHospitalDate(calendar.getTime());
                    } else {//如果是下午，就安排第二前天入院
                        calendar.add(Calendar.DAY_OF_YEAR, +1);
                        apply.setToHospitalDate(calendar.getTime());
                    }
                    applyMapper.updateByPrimaryKeySelective(apply);
                }
                for (int i = 0; i < applies.size(); i++) {
                    Bed bed = unusedBeds.get(i);
                    Apply apply = applies.get(i);
                    bed.setState("1");
                    bed.setPatientId(apply.getPatientId() + "");
                    bedMapper.updateByPrimaryKeySelective(bed);
                }

                result.setSuccess(true);
                result.setMessage("床位安排成功");
            } else {
                result.setMessage("没有符合条件的入院申请");
            }
        } else {
            result.setMessage("没有床位");
        }
        return result;
    }

    @Override
    public CommonResult modifyApply(Apply apply) {
        CommonResult<Object> result = new CommonResult<>();
        int update = applyMapper.updateByPrimaryKeySelective(apply);
        if (update > 0) {
            result.setMessage("成功！");
            result.setSuccess(true);
        } else {
            result.setMessage("失败！");
        }
        return result;
    }
}
