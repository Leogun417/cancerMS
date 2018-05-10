package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Bed;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalGroup;
import com.study.cancer.service.BedService;
import com.study.cancer.service.MedicalGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/group")
public class MedicalGroupController extends BaseController {

    @Resource
    MedicalGroupService medicalGroupService;

    @RequestMapping("/showList")
    public String getList() {
        return "/group_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageInfo getList(Integer page, Integer rows, String name) {
        if ("".equals(name)) {
            name = null;
        }
        CommonResult result = medicalGroupService.showGroupList(page, rows, null, name);
        return (PageInfo) result.getData();
    }

    @RequestMapping("/add")
    @ResponseBody
    public CommonResult add(String name) {
        MedicalGroup group = new MedicalGroup();
        group.setName(name);
        CommonResult result = medicalGroupService.addGroup(group);
        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public CommonResult edit(String name, String groupId) {
        MedicalGroup group = new MedicalGroup();
        group.setName(name);
        group.setId(Integer.parseInt(groupId));
        CommonResult result = medicalGroupService.modifyGroup(group);
        return result;
    }
}
