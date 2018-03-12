package com.study.cancer.service;

import com.github.pagehelper.PageInfo;
import com.study.cancer.model.Apply;
import com.study.cancer.model.Attachment;
import com.study.cancer.model.CommonResult;

import java.util.Map;

public interface AttachmentService {
    CommonResult addAttachment(Attachment attachment);
    CommonResult getAttachmentList(Integer page, Integer rows, String medicalRecordNo, String applyId, String treatmentProcessId);
}
