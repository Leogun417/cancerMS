package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.LeucocyteNeutrophilConfig;
import com.study.cancer.model.LeucocyteNeutrophilConfigWithBLOBs;

public interface LeucocyteNeutrophilConfigService {
    CommonResult select();
    CommonResult add(LeucocyteNeutrophilConfigWithBLOBs leucocyteNeutrophilConfig);
    CommonResult modify(LeucocyteNeutrophilConfigWithBLOBs leucocyteNeutrophilConfig);
}
