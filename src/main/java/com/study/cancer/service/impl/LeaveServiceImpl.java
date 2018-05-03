package com.study.cancer.service.impl;

import com.study.cancer.dao.LeaveDataMapper;
import com.study.cancer.dao.LeaveRecordMapper;
import com.study.cancer.dao.LeucocyteNeutrophilConfigMapper;
import com.study.cancer.dao.MedicalRecordMapper;
import com.study.cancer.model.*;
import com.study.cancer.service.LeaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Resource
    LeaveDataMapper leaveDataMapper;

    @Resource
    LeaveRecordMapper leaveRecordMapper;

    @Resource
    MedicalRecordMapper medicalRecordMapper;

    @Resource
    LeucocyteNeutrophilConfigMapper leucocyteNeutrophilConfigMapper;
    @Override
    public CommonResult addLeaveRecord(LeaveRecord leaveRecord) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = leaveRecordMapper.insertSelective(leaveRecord);
        if (insert > 0) {
            result.setSuccess(true);
            result.setMessage("增加离院记录成功");
        } else {
            result.setMessage("增加离院记录失败");
        }
        return result;
    }

    @Override
    public CommonResult addLeaveData(LeaveData leaveData) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = leaveDataMapper.insertSelective(leaveData);
        if (insert > 0) {
            result.setSuccess(true);
            result.setMessage("增加离院材料成功");
        } else {
            result.setMessage("增加离院材料失败");
        }
        return result;
    }

    @Override
    public CommonResult checkData() {
        CommonResult<Object> result = new CommonResult<>();
        LeucocyteNeutrophilConfig leucocyteNeutrophilConfig = leucocyteNeutrophilConfigMapper.selectByPrimaryKey(1);
        Map<Object, Object> resultMap = new HashMap<>();
        ArrayList<Object> noDataList = new ArrayList<>();//一周内没有提交过材料的就诊记录列表
        ArrayList<Object> leucocyteBelowList = new ArrayList<>();//白细胞低于正常标准的就诊记录列表
        ArrayList<Object> neutrophilBelowList = new ArrayList<>();//中性粒细胞低于正常标准的就诊记录列表
        ArrayList<Object> dangerList = new ArrayList<>();//材料数据显示为危险值的就诊记录列表
        Map map = new HashMap();
        map.put("state", "0");
        map.put("isInHospital", "no");
        List<MedicalRecordListVo> medicalRecordListVos = medicalRecordMapper.selectList(map);
        for (MedicalRecordListVo medicalRecordVo : medicalRecordListVos) {
            List<LeaveData> leaveDataList = leaveDataMapper.selectWeekData(medicalRecordVo.getId() + "");
            if (leaveDataList != null && leaveDataList.size() == 0) {
                noDataList.add(medicalRecordVo);
            } else {
                LeaveData leaveData = leaveDataList.get(0);
                if ("0".equals(leaveData.getIsChecked())) {//本周还未核查过
                    leaveData.setIsChecked("1");
                    if ((Integer.parseInt(leaveData.getLeucocyteConcentration())
                            <= Integer.parseInt(leucocyteNeutrophilConfig.getDangerLeucocyte()))
                            || (Integer.parseInt(leaveData.getNeutrophilConcentration())
                            <= Integer.parseInt(leucocyteNeutrophilConfig.getDangerNeutrophil()))) {//低于危险值
                        dangerList.add(medicalRecordVo);
                    } else {
                        if (Integer.parseInt(leaveData.getLeucocyteConcentration())
                                < Integer.parseInt(leucocyteNeutrophilConfig.getLeucocyte())) {
                            leucocyteBelowList.add(medicalRecordVo);
                        }
                        if (Integer.parseInt(leaveData.getNeutrophilConcentration())
                                < Integer.parseInt(leucocyteNeutrophilConfig.getNeutrophil())) {
                            neutrophilBelowList.add(medicalRecordVo);
                        }
                    }
                }

            }
        }
        resultMap.put("noDataList", noDataList);
        resultMap.put("dangerList", dangerList);
        resultMap.put("leucocyteBelowList", leucocyteBelowList);
        resultMap.put("neutrophilBelowList", neutrophilBelowList);
        result.setData(resultMap);
        return result;
    }

    @Override
    public CommonResult getLeaveDataConfig() {
        CommonResult<Object> result = new CommonResult<>();
        LeucocyteNeutrophilConfigWithBLOBs config = leucocyteNeutrophilConfigMapper.selectByPrimaryKey(1);
        if (config != null) {
            result.setSuccess(true);
            result.setMessage("获取配置成功");
            result.setData(config);
        } else {
            result.setMessage("获取配置失败");
        }
        return result;
    }
}
