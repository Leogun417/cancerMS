package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Apply;
import com.study.cancer.model.ApplyListVo;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.AttachmentService;
import com.study.cancer.service.MedicalRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping(value = "/apply")
public class ApplyController extends BaseController{
    @Resource
    ApplyService applyService;

    @Resource
    MedicalRecordService medicalRecordService;

    @Resource
    AttachmentService attachmentService;
    @RequestMapping("/fillIn")
    public String fillIn(Model model) {
        model.addAttribute("edit", "edit");
        return "/apply";
    }

    @RequestMapping("/showApply/{applyId}")
    public String showApply(Model model, @PathVariable("applyId") Integer applyId) {
        CommonResult result = applyService.getApplyById(applyId);
        if (result.isSuccess()) {
            ApplyListVo applyVo = (ApplyListVo) result.getData();
            model.addAttribute("applyInfo", applyVo);
        }
        return "/apply";
    }

    @RequestMapping("/getAttachments")
    @ResponseBody
    public PageInfo getAttachments(Integer applyId, Integer page, Integer rows) {
        CommonResult attachmentList = attachmentService.getAttachmentList(page, rows,null, applyId + "", null);
        if (attachmentList.isSuccess()) {
            return (PageInfo) attachmentList.getData();
        } else {
            return null;
        }
    }

    @RequestMapping("/myApplyList")
    public String myApplyList() {
        return "/apply_list";
    }

    @RequestMapping("/list")
    public String ApplyList() {
        return "/all_apply_list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(Apply apply, HttpServletRequest request) {
        Integer patientId = getLoginUser().getId();

        apply.setPatientId(patientId);
        apply.setState("0");//0正在排队 1排队完成 2入院 3爽约
        apply.setApplyDate(new Date());
        apply.setToHospitalDate(new Date());
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

    @RequestMapping(value = "/addFiles",method = RequestMethod.POST)
    @ResponseBody
    public String addFiles(Integer applyId, Integer medicalRecordNo, HttpServletRequest request) throws IOException {
        CommonResult result = uploadMore(request, medicalRecordNo, applyId);
        return result.getMessage();
    }

    /**
     *
     * @param page
     * @param rows
     * @param applyStartDate
     * @param applyEndDate
     * @param state  正在排队 排队完成 入院 爽约
     * @return
     */
    @RequestMapping("/getSelfList")
    @ResponseBody
    public PageInfo getSelfList(int page, int rows, String applyStartDate, String applyEndDate, String state) {
        if ("".equals(applyEndDate)) {
            applyEndDate = null;
        }
        if ("".equals(applyStartDate)) {
            applyStartDate = null;
        }
        if ("".equals(state)) {
            state = null;
        }
        PageInfo applyList = applyService.getSelfList(page, rows, getLoginUser().getId(), applyStartDate, applyEndDate, state);
        return applyList;
    }

    @RequestMapping("/getApplyList")
    @ResponseBody
    public PageInfo getApplyList(int page, int rows, String applyStartDate, String applyEndDate, String state, String patientName, Integer medicalRecordNo) {
        if ("".equals(applyEndDate)) {
            applyEndDate = null;
        }
        if ("".equals(applyStartDate)) {
            applyStartDate = null;
        }
        if ("".equals(state)) {
            state = null;
        }
        if ("".equals(patientName)) {
            patientName = null;
        }
        if ("".equals(medicalRecordNo)) {
            medicalRecordNo = null;
        }
        PageInfo applyList = applyService.getList(page, rows, null, applyStartDate, applyEndDate, state, patientName, medicalRecordNo);
        return applyList;
    }

}
