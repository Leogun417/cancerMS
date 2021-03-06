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
        if (oldApplys != null && oldApplys.size() > 0) {
            for (Apply oldApply : oldApplys) {
                String state = oldApply.getState();
                if (state.equals(ApplyStateConstant.WAIT_TO_CHECK_DATA)
                        || state.equals(ApplyStateConstant.WAIT_TO_CHECK_CONDITION)
                        || state.equals(ApplyStateConstant.LINE_UP)
                        || state.equals(ApplyStateConstant.FINISH_LINE_UP)) {
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
    public PageInfo getList(int page, int rows, Integer patientId, String applyStartDate, String applyEndDate, String state, String patientName, Integer medicalRecordNo, String times, String medicalGroup) {
        PageHelper.startPage(page, rows);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("medicalRecordNo", medicalRecordNo);
        map.put("username", patientName);
        map.put("patientId", patientId);
        map.put("applyStartDate", applyStartDate);
        map.put("applyEndDate", applyEndDate);
        map.put("state", state);
        map.put("times", times);
        map.put("medicalGroup", medicalGroup);
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
                    apply.setIsVisible("1");
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

    @Override
    public CommonResult waitDaysStatistics(String year) {
        Map map = new HashMap();
        CommonResult<Object> result = new CommonResult<>();
        map.put("year", year);
        List<ApplyListVo> applyListVos = applyMapper.selectWaitDay(map);
        if (applyListVos != null && applyListVos.size() > 0) {
            result.setMessage("获取平均等待时间成功");
            result.setSuccess(true);
            result.setData(applyListVos);
        } else {
            result.setMessage("获取平均等待时间失败");
        }
        return result;
    }

    @Override
    public CommonResult getNumOfMonth(String year) {
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("year", year);
        List<Integer> maps = applyMapper.selectNumOfMonth(map);
        if (maps != null && maps.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取月总申请成功");
            result.setData(maps);
        } else {
            result.setMessage("获取月总申请失败");
        }
        return result;
    }

    @Override
    public CommonResult getNumOfSeverity(String year, String month) {
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);
        List<ApplyListVo> applyListVos = applyMapper.selectNumOfSeverity(map);
        if (applyListVos != null && applyListVos.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取单月入院量成功");
            result.setData(applyListVos);
        } else {
            result.setMessage("获取单月入院量失败");
        }
        return result;
    }
}
