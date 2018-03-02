package com.study.cancer.service;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Apply;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.User;

import java.util.Date;
import java.util.Map;

public interface ApplyService {
    CommonResult addApply(Apply apply);

    PageInfo getSelfList(int page, int rows, String applyDate, String state);
}