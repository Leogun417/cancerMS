package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.Message;
public interface MessageService {
    CommonResult selectUnreadMessage(String sendTo);
    CommonResult modify(Message message);
    CommonResult selectById(Integer id);
    CommonResult addMessage(Message message);
    CommonResult selectBySendTo(int page, int rows, String sendTo, String sendFromName, String state, String type, String sendStartDate, String sendEndDate);
}
