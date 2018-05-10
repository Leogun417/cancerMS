package com.study.cancer.service.impl;

import com.study.cancer.dao.LeucocyteNeutrophilConfigMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.LeucocyteNeutrophilConfigWithBLOBs;
import com.study.cancer.service.LeucocyteNeutrophilConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LeucocyteNeutrophilConfigServiceImpl implements LeucocyteNeutrophilConfigService {

    @Resource
    LeucocyteNeutrophilConfigMapper leucocyteNeutrophilConfigMapper;

    @Override
    public CommonResult select() {
        CommonResult<Object> result = new CommonResult<>();
        LeucocyteNeutrophilConfigWithBLOBs leucocyteNeutrophilConfigWithBLOBs = leucocyteNeutrophilConfigMapper.selectByPrimaryKey(1);
        if (leucocyteNeutrophilConfigWithBLOBs != null) {
            result.setMessage("获取配置成功");
            result.setSuccess(true);
            result.setData(leucocyteNeutrophilConfigWithBLOBs);
        } else {
            result.setMessage("获取配置失败");
        }
        return result;
    }

    @Override
    public CommonResult add(LeucocyteNeutrophilConfigWithBLOBs leucocyteNeutrophilConfig) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = leucocyteNeutrophilConfigMapper.insertSelective(leucocyteNeutrophilConfig);
        if (insert > 0) {
            result.setMessage("添加配置成功");
            result.setSuccess(true);
        } else {
            result.setMessage("添加配置失败");
        }
        return result;
    }

    @Override
    public CommonResult modify(LeucocyteNeutrophilConfigWithBLOBs leucocyteNeutrophilConfig) {
        CommonResult<Object> result = new CommonResult<>();
        leucocyteNeutrophilConfig.setId(1);
        int update = leucocyteNeutrophilConfigMapper.updateByPrimaryKeyWithBLOBs(leucocyteNeutrophilConfig);
        if (update > 0) {
            result.setMessage("修改配置成功");
            result.setSuccess(true);
        } else {
            result.setMessage("修改配置失败");
        }
        return result;
    }
}
