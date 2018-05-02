package com.study.cancer.service.impl;

import com.study.cancer.dao.LeaveDataMapper;
import com.study.cancer.dao.LeaveRecordMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.LeaveData;
import com.study.cancer.model.LeaveRecord;
import com.study.cancer.service.LeaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class LeaveServiceImpl implements LeaveService {
    @Resource
    LeaveDataMapper leaveDataMapper;

    @Resource
    LeaveRecordMapper leaveRecordMapper;

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
}
