package com.study.cancer.service.impl;

import com.study.cancer.dao.*;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalGroup;
import com.study.cancer.service.MedicalGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MedicalGroupServiceImpl implements MedicalGroupService {

    @Resource
    MedicalGroupMapper medicalGroupMapper;


    @Override
    public CommonResult getGroupList() {
        CommonResult<Object> result = new CommonResult<>();
        List<MedicalGroup> medicalGroups = medicalGroupMapper.selectList();
        if (medicalGroups != null && medicalGroups.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取治疗组列表成功");
            result.setData(medicalGroups);
        } else {
            result.setMessage("获取治疗组列表失败");
        }
        return result;
    }
}
