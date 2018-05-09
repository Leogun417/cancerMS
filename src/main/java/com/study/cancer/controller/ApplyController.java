package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.*;
import com.study.cancer.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/apply")
public class ApplyController extends BaseController {
    @Resource
    ApplyService applyService;

    @Resource
    MedicalRecordService medicalRecordService;

    @Resource
    AttachmentService attachmentService;

    @Resource
    TreatmentProcessService treatmentProcessService;

    @Resource
    MedicalGroupService medicalGroupService;

    @RequestMapping("/fillIn")
    public String fillIn(Model model, String medicalRecordNo) {
        if (medicalRecordNo != null && !"".equals(medicalRecordNo)) {
            model.addAttribute("medicalRecordNo", medicalRecordNo);
        }
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
        CommonResult attachmentList = attachmentService.getAttachmentList(page, rows, null, applyId + "", null);
        if (attachmentList.isSuccess()) {
            return (PageInfo) attachmentList.getData();
        } else {
            return null;
        }
    }

    @RequestMapping("/juge")
    @ResponseBody
    public String juge(String medicalRecordNo, String applyId, String level) {
        TreatmentProcess treatmentProcess = new TreatmentProcess();
        treatmentProcess.setCreateDate(new Date());
        treatmentProcess.setMedicalRecordNo(medicalRecordNo + "");
        treatmentProcess.setDoctorAction("病情危重级别评定为" + level + "级");
        treatmentProcess.setDoctorName(getLoginUser().getUsername());
        CommonResult processResult = treatmentProcessService.addProcess(treatmentProcess);
        if (processResult.isSuccess()) {
            CommonResult result = medicalRecordService.jugeSeverity(medicalRecordNo, level, applyId);
            return result.getMessage();
        } else {
            return processResult.getMessage();
        }
    }

    @RequestMapping("/myApplyList")
    public String myApplyList() {
        return "/apply_list";
    }

    @RequestMapping("/list")
    public String ApplyList(Model model, String again) {
        if (again != null && !"".equals(again)) {
            model.addAttribute("again", "again");
        }
        if (getLoginUser().getAthorization().equals("3")) {
            CommonResult groupList = medicalGroupService.getGroupList();
            if (groupList.isSuccess()) {
                model.addAttribute("medicalGroupList", groupList.getData());
            }
        }
        return "/all_apply_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Apply apply, String medicalRecordNo, HttpServletRequest request) {
        Integer patientId = getLoginUser().getId();
        Date now = new Date();
        apply.setPatientId(patientId);
        apply.setState(ApplyStateConstant.WAIT_TO_CHECK_DATA);//0等待材料审核 1等待病情评估 2正在排队 3排队完成 4入院 5爽约）
        apply.setApplyDate(now);
        apply.setToHospitalDate(now);
        if (medicalRecordNo != null && !"".equals(medicalRecordNo)) {
            apply.setMedicalRecordNo(Integer.parseInt(medicalRecordNo));
            apply.setIsVisible("0");
            apply.setIsAgain("1");
        } else {
            apply.setIsAgain("0");
            apply.setIsVisible("1");
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setPatientId(apply.getPatientId());
            medicalRecord.setState("0");//就诊过程中
            medicalRecord.setIsInHospital("no");
            CommonResult recordResult = medicalRecordService.addRecord(medicalRecord);
            medicalRecordNo = "" + recordResult.getData();
            apply.setMedicalRecordNo(Integer.parseInt(medicalRecordNo));
        }

        CommonResult result = applyService.addApply(apply);
        if (result.isSuccess()) {
            try {
                //此时的request为DefautMultipartHttpServletRequest而不是单纯的HttpServletRequest，否则无法转化为MultipartHttpServletRequest
                CommonResult uploadResult = uploadMore(request, Integer.parseInt(medicalRecordNo), (Integer) ((Map) result.getData()).get("applyId"), "" + ((Map) result.getData()).get("treatmentProcessId"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result.getMessage();
    }

    @RequestMapping(value = "/addFiles", method = RequestMethod.POST)
    @ResponseBody
    public String addFiles(Integer applyId, Integer medicalRecordNo, HttpServletRequest request) throws IOException {
        TreatmentProcess treatmentProcess = new TreatmentProcess();
        treatmentProcess.setCreateDate(new Date());
        treatmentProcess.setMedicalRecordNo(medicalRecordNo + "");
        treatmentProcess.setPatientAction("追加申请材料");
        CommonResult processResult = treatmentProcessService.addProcess(treatmentProcess);
        if (processResult.isSuccess()) {
            TreatmentProcess process = (TreatmentProcess) processResult.getData();
            CommonResult result = uploadMore(request, medicalRecordNo, applyId, process.getId() + "");
            CommonResult userByAuthorization = applyService.getUserByAuthorization("2");
            if (userByAuthorization.isSuccess()) {
                List<User> users = (List<User>) userByAuthorization.getData();
                for (User user : users) {
                    sendMsg(user.getId(), "就诊号为" + medicalRecordNo + "的申请，病人" + ((User) getSession().getAttribute("loginUser")).getUsername() + "的材料已追加", applyId + "号申请_" + ((User) getSession().getAttribute("loginUser")).getUsername(), "/apply/showApply/" + applyId, "1", "0");
                }
            }
            return result.getMessage();
        } else {
            return processResult.getMessage();
        }

    }

    @RequestMapping(value = "/sendForFile", method = RequestMethod.POST)
    @ResponseBody
    public String sendForFile(Integer patientId, String msg, String medicalRecordNo) throws IOException {
        TreatmentProcess treatmentProcess = new TreatmentProcess();
        treatmentProcess.setCreateDate(new Date());
        treatmentProcess.setMedicalRecordNo(medicalRecordNo + "");
        treatmentProcess.setDoctorAction("请求病人不全材料：" + msg);
        treatmentProcess.setDoctorName(getLoginUser().getUsername());
        CommonResult processResult = treatmentProcessService.addProcess(treatmentProcess);
        if (processResult.isSuccess()) {
            sendMsg(patientId, "请追加以下材料：" + msg, TabData.SHOW_APPLY_LIST_FOR_SELF, TabData.tabMap.get(TabData.SHOW_APPLY_LIST_FOR_SELF), "0", "0");
        }
        return processResult.getMessage();
    }

    /**
     * @param page
     * @param rows
     * @param applyStartDate
     * @param applyEndDate
     * @param state          等待材料审核 等待病情评估 正在排队 排队完成 入院 爽约）
     * @return
     */
    @RequestMapping("/getSelfList")
    @ResponseBody
    public PageInfo getSelfList(Integer page, Integer rows, String applyStartDate, String applyEndDate, String state) {
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
    public PageInfo getApplyList(int page, int rows, String applyStartDate, String applyEndDate, String state, String patientName, Integer medicalRecordNo, String times) {
        String medicalGroup = null;
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
        if ("".equals(times) || times == null) {
            medicalGroup = getLoginUser().getMedicalGroup();
        }
        PageInfo applyList = applyService.getList(page, rows, null, applyStartDate, applyEndDate, state, patientName, medicalRecordNo, times, medicalGroup);
        return applyList;
    }

    @RequestMapping("/pass")
    @ResponseBody
    public CommonResult pass(String applyId) {
        Apply apply = new Apply();
        apply.setId(Integer.parseInt(applyId));
        apply.setState(ApplyStateConstant.WAIT_TO_CHECK_CONDITION);
        CommonResult result = applyService.modifyApply(apply);
        return result;
    }

    @RequestMapping("/inHospital")
    @ResponseBody
    public CommonResult inHospital(String group, String treatmentPlan, String applyId, String medicalRecordNo, String patientId, String treatmentTimes) {
        CommonResult<Object> result = new CommonResult<>();
        Apply apply = new Apply();
        User patient = new User();
        CommonResult recordData = medicalRecordService.getRecord(medicalRecordNo);
        if (recordData.isSuccess()) {
            MedicalRecord medicalRecord = (MedicalRecord) recordData.getData();
            apply.setId(Integer.parseInt(applyId));
            apply.setState(ApplyStateConstant.TO_HOSPITAL);
            CommonResult modifyApplyData = applyService.modifyApply(apply);
            if (!modifyApplyData.isSuccess()) {
                return modifyApplyData;
            }
            medicalRecord.setId(Integer.parseInt(medicalRecordNo));
            medicalRecord.setIsInHospital("yes");
            if (treatmentTimes != null) {
                if (Integer.parseInt(treatmentTimes) < medicalRecord.getToHospitalTimes() + 1) {
                    result.setMessage("评估方案时间错误");
                    return result;
                }
                medicalRecord.setTreatmentTimes(Integer.parseInt(treatmentTimes));
            }
            if (treatmentPlan != null) {
                medicalRecord.setTreatmentPlan(treatmentPlan);
            }
            medicalRecord.setToHospitalTimes(medicalRecord.getToHospitalTimes() + 1);
            if (group != null && !"".equals(group)) {
                patient.setId(Integer.parseInt(patientId));
                patient.setMedicalGroup(group);
                CommonResult modifyUserData = loginService.modifyUserInfo(patient);
                if (!modifyUserData.isSuccess()) {
                    return modifyUserData;
                }
            }
            CommonResult editRecordData = medicalRecordService.editRecord(medicalRecord);
            if (editRecordData.isSuccess()) {
                editRecordData.setMessage("入院成功");
                TreatmentProcess treatmentProcess = new TreatmentProcess();
                treatmentProcess.setDoctorAction("制定治疗方案：" + treatmentPlan);
                treatmentProcess.setPatientAction("入院");
                treatmentProcess.setMedicalRecordNo(medicalRecordNo);
                treatmentProcess.setDoctorName(getLoginUser().getUsername());
                treatmentProcessService.addProcess(treatmentProcess);
                return editRecordData;
            } else {
                result.setMessage("入院失败");
                return result;
            }
        } else {
            result.setMessage("无治疗记录");
            return result;
        }
    }

    @RequestMapping("/showStatistic")
    public String showStatistic() {
        return "/statistics";
    }

    @RequestMapping("/getStatisticsData")
    @ResponseBody
    public CommonResult getStatisticsData(String year) {
        CommonResult<Object> statisticsResult = new CommonResult<>();
        CommonResult result = applyService.waitDaysStatistics(year);
        CommonResult numOfMonth = applyService.getNumOfMonth(year);
        if (!numOfMonth.isSuccess()) {
            statisticsResult.setMessage(numOfMonth.getMessage());
            return statisticsResult;
        }
        if (result.isSuccess()) {
            List<ApplyListVo> datas = (List<ApplyListVo>) result.getData();
            Map<Object, Object> resulMap = new HashMap<>();
            ArrayList<Object> oneLevel = new ArrayList<>();
            ArrayList<Object> twoLevel = new ArrayList<>();
            ArrayList<Object> treeLevel = new ArrayList<>();
            ArrayList<Object> fourLevel = new ArrayList<>();
            ArrayList<Object> monthList = new ArrayList<>();
            int one=0, two=0, tree=0, four=0;
            for (ApplyListVo data : datas) {
                if (!monthList.contains(data.getToMonth())) {
                    monthList.add(data.getToMonth());
                }
                int toMonth = Integer.parseInt(data.getToMonth());
                int minMonth = Integer.parseInt((String) monthList.get(0));
                if (data.getSeverity().equals("1")) {
                    while (toMonth > minMonth && one == 0) {
                        toMonth--;
                        oneLevel.add("");
                    }
                    one++;
                    oneLevel.add(data.getWaitDays());
                } else if (data.getSeverity().equals("2")) {
                    while (toMonth > minMonth && two == 0) {
                        toMonth--;
                        twoLevel.add("");
                    }
                    two++;
                    twoLevel.add(data.getWaitDays());
                } else if (data.getSeverity().equals("3")) {
                    while (toMonth > minMonth && tree == 0) {
                        toMonth--;
                        treeLevel.add("");
                    }
                    tree++;
                    treeLevel.add(data.getWaitDays());
                } else {
                    while (toMonth > minMonth && four == 0) {
                        toMonth--;
                        fourLevel.add("");
                    }
                    four++;
                    fourLevel.add(data.getWaitDays());
                }
            }
            resulMap.put("oneLevel", oneLevel);
            resulMap.put("twoLevel", twoLevel);
            resulMap.put("treeLevel", treeLevel);
            resulMap.put("fourLevel", fourLevel);
            resulMap.put("monthList", monthList);
            resulMap.put("monthNumList", numOfMonth.getData());
            statisticsResult.setSuccess(true);
            statisticsResult.setData(resulMap);
        }
        return statisticsResult;
    }

    @RequestMapping("/getPieData")
    @ResponseBody
    public CommonResult getPieData(String year, String month) {
        CommonResult<Object> result = new CommonResult<>();
        Map<Object, Object> resultMap = new HashMap<>();
        CommonResult numOfSeverity = applyService.getNumOfSeverity(year, month);
        if (numOfSeverity.isSuccess()) {
            List<ApplyListVo> numOfSeverityData = (List<ApplyListVo>) numOfSeverity.getData();
            for (ApplyListVo applyListVo : numOfSeverityData) {
                if (applyListVo.getSeverity().equals("1")) {
                    resultMap.put("one", applyListVo.getNumOfSeverity());
                } else if (applyListVo.getSeverity().equals("2")) {
                    resultMap.put("two", applyListVo.getNumOfSeverity());
                } else if (applyListVo.getSeverity().equals("3")) {
                    resultMap.put("tree", applyListVo.getNumOfSeverity());
                } else if (applyListVo.getSeverity().equals("4")) {
                    resultMap.put("four", applyListVo.getNumOfSeverity());
                }
            }
            result.setMessage(numOfSeverity.getMessage());
            result.setSuccess(numOfSeverity.isSuccess());
            result.setData(resultMap);
        }
        return result;
    }

}
