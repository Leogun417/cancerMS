package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Apply;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.MedicalRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/apply")
public class ApplyController extends BaseController{
    @Resource
    ApplyService applyService;

    @Resource
    MedicalRecordService medicalRecordService;
    @RequestMapping("/fillIn")
    public String fillIn() {
        return "/apply";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(Apply apply, HttpServletRequest request) {
        Integer patientId = getLoginUser().getId();

        apply.setPatientId(patientId);
        apply.setState("0");//0正在排队 1排队完成 2入院 3爽约
        apply.setApplyDate(new Date());
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatientId(apply.getPatientId());
        medicalRecord.setIsInHospital("no");
        CommonResult recordResult = medicalRecordService.addRecord(medicalRecord);
        apply.setMedicalRecordNo((Integer) recordResult.getData());
        CommonResult result = applyService.addApply(apply);
        if (result.isSuccess()) {
            try {
                //此时的request为DefautMultipartHttpServletRequest而不是单纯的HttpServletRequest，否则无法转化为MultipartHttpServletRequest
                CommonResult uploadResult = uploadMore(request, (Integer) recordResult.getData(), (Integer) result.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.getMessage();
    }

    /**
     *
     * @param page
     * @param rows
     * @param applyDate
     * @param state 正在排队 排队完成 入院 爽约
     * @return
     */
    @RequestMapping("/getSelfList")
    @ResponseBody
    public PageInfo getSelfList(int page, int rows, String applyDate, String state) {
        PageInfo applyList = applyService.getSelfList(page, rows, applyDate, state);
        return applyList;
    }

}
