package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.MedicalGroup;
import com.study.cancer.model.Message;

public interface MedicalGroupService {
   CommonResult getGroupList();
   CommonResult addGroup(MedicalGroup medicalGroup);

   CommonResult showGroupList(int page, int rows, String groupId, String name);

   CommonResult modifyGroup(MedicalGroup medicalGroup);
}
