package com.study.cancer.service.impl;

import com.study.cancer.dao.MessageMapper;
import com.study.cancer.model.*;
import com.study.cancer.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    MessageMapper messageMapper;

    @Override
    public CommonResult addMessage(Message message) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = messageMapper.insertSelective(message);
        if (insert > 0) {
            result.setSuccess(true);
            result.setMessage("消息插入成功");
        } else {
            result.setMessage("消息插入失败");
        }
        return result;
    }
}
