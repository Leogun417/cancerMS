package com.study.cancer.service.impl;

import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.dao.AttachmentMapper;
import com.study.cancer.model.Apply;
import com.study.cancer.model.Attachment;
import com.study.cancer.model.CommonResult;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.AttachmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    AttachmentMapper attachmentMapper;

    @Override
    public CommonResult addAttachment(Attachment attachment) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = attachmentMapper.insert(attachment);
        if (insert > 0) {
            result.setSuccess(true);
            result.setData(insert);
            result.setMessage("附件上传成功");
        } else {
            result.setMessage("附件上传失败");
        }
        return result;
    }
}
