package com.study.cancer.service;

import com.study.cancer.model.Apply;
import com.study.cancer.model.Attachment;
import com.study.cancer.model.CommonResult;

public interface AttachmentService {
    CommonResult addAttachment(Attachment attachment);
}
