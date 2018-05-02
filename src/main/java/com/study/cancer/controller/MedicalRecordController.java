package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.*;
import com.study.cancer.service.LeaveService;
import com.study.cancer.service.MedicalRecordService;
import com.study.cancer.service.TreatmentProcessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/record")
public class MedicalRecordController extends BaseController {
    @Resource
    MedicalRecordService medicalRecordService;

    @Resource
    LeaveService leaveService;

    @Resource
    TreatmentProcessService treatmentProcessService;

    @RequestMapping("/recordList")
    public String recordList() {
        return "/record_list";
    }

    @RequestMapping("/showList")
    @ResponseBody
    public PageInfo showList(int page, int rows, String patientName, String phoneNumber, String medicalRecordNo, String state) {
        String medicalGroup = null;
        if ("".equals(medicalRecordNo)) {
            medicalRecordNo = null;
        }
        if ("".equals(state)) {
            state = null;
        }
        if (!"1".equals(getLoginUser().getAthorization())) {//非病人
            if ("2".equals(getLoginUser().getAthorization())) {//普通治疗组医生查看本组的病人
                medicalGroup = getLoginUser().getMedicalGroup();
            }
            if ("".equals(patientName)) {
                patientName = null;
            }
            if ("".equals(phoneNumber)) {
                phoneNumber = null;
            }
        } else {
            patientName = null;
            phoneNumber = getLoginUser().getPhoneNumber();
        }
        CommonResult result = medicalRecordService.showRecordList(page, rows, patientName, phoneNumber, medicalRecordNo, medicalGroup, state);
        return (PageInfo) result.getData();
    }

    @RequestMapping("/toHospitalCheck")
    @ResponseBody
    public CommonResult toHospitalCheck(String medicalRecordNo) {
        CommonResult recordResult = medicalRecordService.getRecord(medicalRecordNo);
        CommonResult<Object> result = new CommonResult<>();
        if (recordResult.isSuccess()) {
            MedicalRecord medicalRecord = (MedicalRecord) recordResult.getData();
            result.setData(medicalRecord);
            Integer toHospitalTimes = medicalRecord.getToHospitalTimes();
            Integer treatmentTimes = medicalRecord.getTreatmentTimes();
            int thisTime = toHospitalTimes + 1;
            if (toHospitalTimes == 0) {//首次入院
                result.setMessage("0");
            } else if (treatmentTimes == thisTime) {
                result.setMessage("1");
            } else {//非首次入院，但不需要评估方案
                result.setMessage("3");
            }
            return result;
        } else {
            return recordResult;
        }
    }

    @RequestMapping("/leave")
    @ResponseBody
    public String leave(String state, String medicalRecordNo, String nextToHospitalDate, String leucocyteConcentration, String neutrophilConcentration) {
        if ("0".equals(state)) {
            LeaveData leaveData = new LeaveData();
            leaveData.setLeucocyteConcentration(leucocyteConcentration);
            leaveData.setNeutrophilConcentration(neutrophilConcentration);
            leaveData.setMedicalRecordNo(medicalRecordNo);
            CommonResult leaveDateResult = leaveService.addLeaveData(leaveData);
            if (!leaveDateResult.isSuccess()) {
                return leaveDateResult.getMessage();
            }
            LeaveRecord leaveRecord = new LeaveRecord();
            leaveRecord.setMedicalRecordNo(medicalRecordNo);
            leaveRecord.setType(state);
            CommonResult leaveRecordResult = leaveService.addLeaveRecord(leaveRecord);
            if (!leaveRecordResult.isSuccess()) {
                return leaveRecordResult.getMessage();
            }
        }
        TreatmentProcess treatmentProcess = new TreatmentProcess();
        treatmentProcess.setDoctorName(getLoginUser().getUsername());
        treatmentProcess.setMedicalRecordNo(medicalRecordNo);
        treatmentProcess.setPatientAction("离院");
        treatmentProcess.setDoctorAction("审核离院");
        CommonResult processResult = treatmentProcessService.addProcess(treatmentProcess);
        if (!processResult.isSuccess()) {
            return processResult.getMessage();
        }
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setState(state);
        medicalRecord.setId(Integer.parseInt(medicalRecordNo));
        medicalRecord.setIsInHospital("no");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nextDate = null;
        try {
            nextDate = simpleDateFormat.parse(nextToHospitalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        medicalRecord.setNextToHospitalDate(nextDate);
        CommonResult result = medicalRecordService.editRecord(medicalRecord);
        if (result.isSuccess()) {
            return "离院成功";
        }
        return "离院失败";
    }

}
