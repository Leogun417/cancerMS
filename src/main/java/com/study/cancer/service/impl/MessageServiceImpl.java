package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.MessageMapper;
import com.study.cancer.model.*;
import com.study.cancer.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    MessageMapper messageMapper;

    @Override
    public CommonResult selectUnreadMessage(String sendTo) {
        CommonResult<Object> result = new CommonResult<>();
        Map map = new HashMap();
        map.put("sendTo", sendTo);
        map.put("state", "0");
        List<Message> messages = messageMapper.selectBySendTo(map);
        if (messages != null && messages.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取到未读消息");
        } else {
            result.setMessage("未获取到未读消息");
        }
        return result;
    }

    @Override
    public CommonResult modify(Message message) {
        CommonResult<Object> result = new CommonResult<>();
        int update = messageMapper.updateByPrimaryKeySelective(message);
        if (update > 0) {
            result.setMessage("修改成功");
            result.setSuccess(true);
        } {
            result.setMessage("修改失败");
        }
        return result;
    }

    @Override
    public CommonResult selectById(Integer id) {
        CommonResult<Object> result = new CommonResult<>();
        Message message = messageMapper.selectByPrimaryKey(id);
        if (message != null) {
            result.setData(message);
            result.setMessage("获取消息成功");
            result.setSuccess(true);
        } else {
            result.setMessage("获取消息失败");
        }
        return result;
    }

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

    @Override
    public CommonResult selectBySendTo(int page, int rows, String sendTo, String sendFromName, String state, String type, String sendStartDate, String sendEndDate) {
        PageHelper.startPage(page, rows);
        HashMap<Object, Object> map = new HashMap<>();
        CommonResult<Object> result = new CommonResult<>();
        map.put("sendTo", sendTo);
        map.put("state", state);
        map.put("type", type);
        map.put("sendFromName", sendFromName);
        map.put("sendStartDate", sendStartDate);
        map.put("sendEndDate", sendEndDate);
        List<Message> messages = messageMapper.selectBySendTo(map);
        PageInfo<Message> messagePageInfo = new PageInfo<>(messages);
        result.setData(messagePageInfo);
        if (messages != null & messages.size() > 0) {
            result.setSuccess(true);
            result.setMessage("获取信息成功!");
        } else {
            result.setMessage("获取信息失败!");
        }
        return result;
    }


}
