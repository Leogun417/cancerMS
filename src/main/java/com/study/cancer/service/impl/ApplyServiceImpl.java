package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.model.Apply;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    ApplyMapper applyMapper;

    @Override
    public CommonResult addApply(Apply apply) {
        CommonResult<Object> result = new CommonResult<>();
        List<Apply> oldApplys = applyMapper.selectByPatientId(apply.getPatientId());
        if (oldApplys != null && oldApplys.size() > 0) {//0正在排队 1排队完成 2入院 3爽约
            for (Apply oldApply : oldApplys) {
                if (oldApply.getState().equals("0")) {
                    result.setMessage("申请正在排队，请勿重复提交");
                    break;
                }
            }

        } else {
            int insert = applyMapper.insert(apply);
            if (insert > 0) {
                result.setSuccess(true);
                result.setData(apply.getId());
                result.setMessage("申请提交成功");
            } else {
                result.setMessage("申请提交失败");
            }
        }
        return result;
    }

    @Override
    public PageInfo getSelfList(int page, int rows, String applyDate, String state) {
        PageHelper.startPage(page, rows);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("applyDate", applyDate);
        map.put("state", state);
        List<Apply> applyList = applyMapper.selectList(map);
        return new PageInfo(applyList);
    }
}
