package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.CommonResult;
import com.study.cancer.service.TreatmentProcessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/process")
public class TreatmentProcessController extends BaseController {
    @Resource
    TreatmentProcessService treatmentProcessService;

    @RequestMapping("/processList")
    public String processList(String medicalRecordNo, Model model) {
        model.addAttribute("medicalRecordNo", medicalRecordNo);
        return "/process_list";
    }

    @RequestMapping("/showList")
    @ResponseBody
    public PageInfo showList(int page, int rows, String medicalRecordNo) {
        CommonResult result = treatmentProcessService.showProcessList(page, rows, medicalRecordNo);
        return (PageInfo) result.getData();
    }
}
