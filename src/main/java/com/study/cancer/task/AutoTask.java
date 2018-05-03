package com.study.cancer.task;

import com.aliyuncs.exceptions.ClientException;
import com.study.cancer.controller.BaseController;
import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.dao.BedMapper;
import com.study.cancer.dao.UserMapper;
import com.study.cancer.model.*;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.LeaveService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动安排入院、材料检查、入院提醒的定时器
 */
@Component
public class AutoTask {
    @Resource
    ApplyService applyService;

    @Resource
    BaseController baseController;

    @Resource
    LeaveService leaveService;

    @Resource
    UserMapper userMapper;
    /**
     * CronTrigger配置完整格式为： [秒] [分] [小时] [日] [月] [周] [年]
     * * 表示所有值. 例如:在分的字段上设置 "*",表示每一分钟都会触发。
     * <p>
     * <p>
     * ? 表示不指定值。使用的场景为不需要关心当前设置这个字段的值。
     * <p>
     * 例如:要在每月的10号触发一个操作，但不关心是周几，所以需要周位置的那个字段设置为"?" 具体设置为 0 0 0 10 * ?
     * <p>
     * <p>
     * - 表示区间。例如 在小时上设置 "10-12",表示 10,11,12点都会触发。
     * <p>
     * <p>
     * , 表示指定多个值，例如在周字段上设置 "MON,WED,FRI" 表示周一，周三和周五触发
     * <p>
     * <p>
     * / 用于递增触发。如在秒上面设置"5/15" 表示从5秒开始，每增15秒触发(5,20,35,50)。 在月字段上设置'1/3'所示每月1号开始，每隔三天触发一次。
     * <p>
     * <p>
     * L 表示最后的意思。在日字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]), 在周字段上表示星期六，相当于"7"或"SAT"。如果在"L"前加上数字，则表示该数据的最后一个。例如在周字段上设置"6L"这样的格式,则表示“本月最后一个星期五"
     * <p>
     * <p>
     * W 表示离指定日期的最近那个工作日(周一至周五). 例如在日字段上设置"15W"，表示离每月15号最近的那个工作日触发。如果15号正好是周六，则找最近的周五(14号)触发, 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。如果指定格式为 "1W",它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注，"W"前只能设置具体的数字,不允许区间"-").
     * <p>
     * <p>
     * # 序号(表示每月的第几个周几)，例如在周字段上设置"6#3"表示在每月的第三个周六.注意如果指定"#5",正好第五周没有周六，则不会触发该配置(用在母亲节和父亲节再合适不过了) ；
     * <p>
     * 小提示：
     * 'L'和 'W'可以组合使用。如果在日字段上设置"LW",则表示在本月的最后一个工作日触发；
     * 周字段的设置，若使用英文字母是不区分大小写的，即MON 与mon相同；
     *
     * 解决定时任务执行两次问题：
     * 因为在将定时任务配置进了springMVC核心配置文件，而这个文件会被加载两次，
     * 第一次是在web容器启动的时候，第二次是spring本身加载，所以启动了两个定时线程。
     * 解决办法就是将定时任务配置独立出来，放到web.xml中通过context-param去加载，而不是通过spring加载
     */
    @Scheduled(cron = "0 7 16 * * ? ")
    public void test() {
        System.out.println("定时任务");
    }

    @Scheduled(cron = "0 15 0/1 * * ? ")
    public void ArrangeInHospital() {
        applyService.arrange();
    }

    @Scheduled(cron = "0 10 0 * * ? ")
    public void checkLeavePatientData() throws ClientException {
        CommonResult result = leaveService.checkData();
        CommonResult leaveDataConfig = leaveService.getLeaveDataConfig();
        LeucocyteNeutrophilConfigWithBLOBs configWithBLOBs = null;
        if (leaveDataConfig.isSuccess()) {
            configWithBLOBs = (LeucocyteNeutrophilConfigWithBLOBs) leaveDataConfig.getData();
        }
        Map resultMap = (Map) result.getData();
        List<MedicalRecordListVo> noDataList = (List) resultMap.get("noDataList");
        List<MedicalRecordListVo> dangerList = (List) resultMap.get("dangerList");
        List<MedicalRecordListVo> leucocyteBelowList = (List) resultMap.get("leucocyteBelowList");
        List<MedicalRecordListVo> neutrophilBelowList = (List) resultMap.get("neutrophilBelowList");
        for (MedicalRecordListVo medicalRecordListVo : noDataList) {
            baseController.sendMsg(medicalRecordListVo.getPatientId(), "超过一周未提交材料，请尽快登录系统提交！", TabData.SHOW_MEDICAL_RECORD, TabData.tabMap.get(TabData.SHOW_MEDICAL_RECORD), "0", "0", "系统消息");
        }
        for (MedicalRecordListVo medicalRecordListVo : dangerList) {
            String medicalGroup = medicalRecordListVo.getMedicalGroup();
            String patientName = medicalRecordListVo.getUsername();
            Integer patientId = medicalRecordListVo.getPatientId();
            String patientPhone = medicalRecordListVo.getPhoneNumber();
            String toDoctorMsg = patientId + "号患者" + patientName + "提交的指标已经到达危险值，请尽快联系患者处理！患者联系电话：" + patientPhone;
            String toPatientMsg = "您提交的指标已经到达危险值，请尽快联系医生处理！所属治疗组医生联系电话：";
            Map<Object, Object> map = new HashMap<>();
            map.put("medicalGroup", medicalGroup);
            map.put("athorization", "2");
            map.put("isGroupLeader", "1");
            Map<Object, Object> doctorSMSMap = new HashMap<>();//构造用于向医生发送短信的数据map
            doctorSMSMap.put("patientId", patientId);
            doctorSMSMap.put("patientName", patientName);
            doctorSMSMap.put("patientPhone", patientPhone);

            List<User> doctors = userMapper.selectByGroup(map);
            for (User doctor : doctors) {
                Map<Object, Object> patientSMSMap = new HashMap<>();//构造用于向患者发送短信的数据map
                patientSMSMap.put("doctorName", doctor.getUsername());
                patientSMSMap.put("doctorPhone", doctor.getPhoneNumber());
                baseController.sendSMS(patientPhone, patientSMSMap, SMSConstant.NOTICE_PATIENT_CONTACT_DOCTOR);
                baseController.sendMsg(patientId, toPatientMsg + doctor.getUsername() + "--" + doctor.getPhoneNumber(), TabData.SHOW_MEDICAL_RECORD, TabData.tabMap.get(TabData.SHOW_MEDICAL_RECORD), "0", "0", "系统消息");
                baseController.sendSMS(doctor.getPhoneNumber(), doctorSMSMap, SMSConstant.NOTICE_DOCTOR_CONTACT_PATIENT);
                baseController.sendMsg(doctor.getId(), toDoctorMsg, TabData.SHOW_MEDICAL_RECORD, TabData.tabMap.get(TabData.SHOW_MEDICAL_RECORD), "0", "0", "系统消息");
            }
        }

        for (MedicalRecordListVo medicalRecordListVo : leucocyteBelowList) {
            String suggestion = null;
            if (neutrophilBelowList.contains(medicalRecordListVo)) {
                suggestion = configWithBLOBs.getNeutrophilSuggestion();
            }
            baseController.sendMsg(medicalRecordListVo.getPatientId(), suggestion + "," + configWithBLOBs.getLeucocyteSuggestion(), TabData.SHOW_MEDICAL_RECORD, TabData.tabMap.get(TabData.SHOW_MEDICAL_RECORD), "0", "0", "系统消息");
        }
    }
}

