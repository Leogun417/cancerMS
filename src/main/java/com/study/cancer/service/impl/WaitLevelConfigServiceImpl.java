package com.study.cancer.service.impl;

import com.study.cancer.dao.WaitLevelConfigMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.WaitLevelConfig;
import com.study.cancer.service.WaitLevelConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WaitLevelConfigServiceImpl implements WaitLevelConfigService {

    @Resource
    WaitLevelConfigMapper waitLevelConfigMapper;

    @Override
    public CommonResult select() {
        CommonResult<Object> result = new CommonResult<>();
        WaitLevelConfig waitLevelConfig = waitLevelConfigMapper.selectByPrimaryKey(1);
        if (waitLevelConfig != null) {
            result.setMessage("获取配置成功");
            result.setSuccess(true);
            result.setData(waitLevelConfig);
        } else {
            result.setMessage("获取配置失败");
        }
        return result;
    }

    @Override
    public CommonResult add(WaitLevelConfig waitLevelConfig) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = waitLevelConfigMapper.insertSelective(waitLevelConfig);
        if (insert > 0) {
            result.setMessage("添加配置成功");
            result.setSuccess(true);
        } else {
            result.setMessage("添加配置失败");
        }
        return result;
    }

    @Override
    public CommonResult modify(WaitLevelConfig waitLevelConfig) {
        CommonResult<Object> result = new CommonResult<>();
        waitLevelConfig.setId(1);
        int update = waitLevelConfigMapper.updateByPrimaryKeySelective(waitLevelConfig);
        if (update > 0) {
            result.setMessage("修改配置成功");
            result.setSuccess(true);
        } else {
            result.setMessage("修改配置失败");
        }
        return result;
    }
}
