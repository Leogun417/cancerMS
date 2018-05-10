package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Bed;
import com.study.cancer.model.CommonResult;
import com.study.cancer.service.BedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/bed")
public class BedController extends BaseController {

    @Resource
    BedService bedService;

    @RequestMapping("/showList")
    public String getList() {
        return "/bed_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageInfo getList(Integer page, Integer rows, String bedId, String state, String patientId, String bedNo) {
        if ("".equals(bedId)) {
            bedId = null;
        }
        if ("".equals(state)) {
            state = null;
        }
        if ("".equals(patientId)) {
            patientId = null;
        }
        if ("".equals(bedNo)) {
            bedNo = null;
        }
        CommonResult result = bedService.showBedList(page, rows, bedId, state, patientId, bedNo);
        return (PageInfo) result.getData();
    }

    @RequestMapping("/add")
    @ResponseBody
    public CommonResult add(String bedNo) {
        Bed bed = new Bed();
        bed.setBedNo(bedNo);
        bed.setState("0");
        CommonResult result = bedService.addBed(bed);
        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public CommonResult edit(String bedNo, String bedId) {
        Bed bed = new Bed();
        bed.setBedNo(bedNo);
        bed.setId(Integer.parseInt(bedId));
        CommonResult result = bedService.modifyBed(bed);
        return result;
    }
}
