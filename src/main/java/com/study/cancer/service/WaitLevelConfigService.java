package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.WaitLevelConfig;

public interface WaitLevelConfigService {
    CommonResult select();
    CommonResult add(WaitLevelConfig waitLevelConfig);
    CommonResult modify(WaitLevelConfig waitLevelConfig);
}
