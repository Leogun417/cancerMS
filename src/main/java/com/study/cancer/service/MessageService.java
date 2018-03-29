package com.study.cancer.service;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Apply;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.Message;

public interface MessageService {
    CommonResult addMessage(Message message);
}
