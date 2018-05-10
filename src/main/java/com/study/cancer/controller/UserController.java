package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Bed;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.User;
import com.study.cancer.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
    @Resource
    UserService userService;

    @RequestMapping("/showList")
    public String getList() {
        return "/user_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageInfo getList(Integer page, Integer rows, String username, String phoneNumber, String athorization) {
        if ("".equals(username)) {
            username = null;
        }
        if ("".equals(phoneNumber)) {
            phoneNumber = null;
        }
        if ("".equals(athorization)) {
            athorization = null;
        }
        CommonResult result = userService.showDoctorList(page, rows, username, phoneNumber, athorization);
        return (PageInfo) result.getData();
    }

    @RequestMapping("/edit")
    @ResponseBody
    public CommonResult edit(String athorization, String id) {
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setAthorization(athorization);
        CommonResult result = userService.modifyUser(user);
        return result;
    }

    @RequestMapping("/divideGroup")
    @ResponseBody
    public String divideGroup(String doctorIds, String groupId) {
        String substring = doctorIds.substring(1, doctorIds.lastIndexOf("]"));
        String[] userIds = substring.split(",");
        userService.divideGroup(userIds, groupId);
        return "success";
    }
}
