package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.*;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalGroup;
import com.study.cancer.service.MedicalGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalGroupServiceImpl implements MedicalGroupService {

    @Resource
    MedicalGroupMapper medicalGroupMapper;


    @Override
    public CommonResult getGroupList() {
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> map = new HashMap<>();
        List<MedicalGroup> medicalGroups = medicalGroupMapper.selectList(map);
        if (medicalGroups != null && medicalGroups.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取治疗组列表成功");
            result.setData(medicalGroups);
        } else {
            result.setMessage("获取治疗组列表失败");
        }
        return result;
    }

    @Override
    public CommonResult addGroup(MedicalGroup medicalGroup) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = medicalGroupMapper.insertSelective(medicalGroup);
        if (insert > 0) {
            result.setSuccess(true);
            result.setMessage("增加治疗组成功");
        } else {
            result.setMessage("增加治疗组失败");
        }
        return result;
    }

    @Override
    public CommonResult showGroupList(int page, int rows, String groupId, String name) {
        PageHelper.startPage(page, rows);
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("name", name);
        List<MedicalGroup> beds = medicalGroupMapper.selectList(map);
        result.setData(new PageInfo<>(beds));
        if (beds != null && beds.size() > 0) {
            result.setMessage("查询治疗组成功");
            result.setSuccess(true);

        } else {
            result.setMessage("查询治疗组失败");
        }
        return result;
    }

    @Override
    public CommonResult modifyGroup(MedicalGroup medicalGroup) {
        CommonResult<Object> result = new CommonResult<>();
        int update = medicalGroupMapper.updateByPrimaryKeySelective(medicalGroup);
        if (update > 0) {
            result.setMessage("修改治疗组信息成功");
            result.setSuccess(true);
        } else {
            result.setMessage("修改治疗组信息失败");
        }
        return result;
    }
}
