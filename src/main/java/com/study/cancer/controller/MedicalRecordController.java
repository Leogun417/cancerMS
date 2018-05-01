package com.study.cancer.controller;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalRecord;
import com.study.cancer.service.MedicalRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/record")
public class MedicalRecordController extends BaseController {
    @Resource
    MedicalRecordService medicalRecordService;

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

}
