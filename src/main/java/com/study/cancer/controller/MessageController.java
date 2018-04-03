package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.Message;
import com.study.cancer.model.User;
import com.study.cancer.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {
    @Resource
    MessageService service;

    @RequestMapping("/list")
    public String messageList() {
        return "/message_list";
    }

    @RequestMapping("/getMessageList")
    @ResponseBody
    public PageInfo getMessageList(int page, int rows, String sendFromName, String state, String type, String sendStartDate, String sendEndDate) {
        if ("".equals(sendFromName)) {
            sendFromName = null;
        }
        if ("".equals(state)) {
            state = null;
        }
        if ("".equals(type)) {
            type = null;
        }
        if ("".equals(sendStartDate)) {
            sendStartDate = null;
        }
        if ("".equals(sendEndDate)) {
            sendEndDate = null;
        }
        User loginUser = (User) getSession().getAttribute("loginUser");
        CommonResult result = messageService.selectBySendTo(page, rows, loginUser.getId() + "", sendFromName, state, type, sendStartDate, sendEndDate);
        return (PageInfo) result.getData();

    }

    @RequestMapping("/checkUnreadMsg")
    @ResponseBody
    public Boolean checkUnreadMsg() {
        CommonResult result = messageService.selectUnreadMessage(((User) getSession().getAttribute("loginUser")).getId() + "");
        return result.isSuccess();
    }

    @RequestMapping("/changeState")
    @ResponseBody
    public String changeState(Integer messageId) {
        CommonResult result = messageService.selectById(messageId);
        if (result.isSuccess()) {
            Message message = (Message) result.getData();
            message.setState("1");//已读
            CommonResult modify = messageService.modify(message);
            return modify.getMessage();
        }
        return result.getMessage();
    }
}
