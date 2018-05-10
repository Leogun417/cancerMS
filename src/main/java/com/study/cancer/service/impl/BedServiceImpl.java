package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.BedMapper;
import com.study.cancer.model.Bed;
import com.study.cancer.model.CommonResult;
import com.study.cancer.service.BedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BedServiceImpl implements BedService {

    @Resource
    BedMapper bedMapper;

    @Override
    public CommonResult addBed(Bed bed) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = bedMapper.insertSelective(bed);
        if (insert > 0) {
            result.setSuccess(true);
            result.setMessage("增加床位成功");
        } else {
            result.setMessage("增加床位失败");
        }
        return result;
    }

    @Override
    public CommonResult showBedList(int page, int rows, String bedId, String state, String patientId, String bedNo) {
        PageHelper.startPage(page, rows);
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bedId", bedId);
        map.put("state", state);
        map.put("patientId", patientId);
        map.put("bedNo", bedNo);
        List<Bed> beds = bedMapper.selectList(map);
        result.setData(new PageInfo<>(beds));
        if (beds != null && beds.size() > 0) {
            result.setMessage("查询床位成功");
            result.setSuccess(true);

        } else {
            result.setMessage("查询床位失败");
        }
        return result;
    }

    @Override
    public CommonResult modifyBed(Bed bed) {
        CommonResult<Object> result = new CommonResult<>();
        int update = bedMapper.updateByPrimaryKeySelective(bed);
        if (update > 0) {
            result.setMessage("修改床位信息");
            result.setSuccess(true);
        } else {
            result.setMessage("修改床位信息失败");
        }
        return result;
    }
}
