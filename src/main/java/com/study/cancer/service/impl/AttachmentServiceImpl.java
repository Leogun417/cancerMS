package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.ApplyMapper;
import com.study.cancer.dao.AttachmentMapper;
import com.study.cancer.model.Apply;
import com.study.cancer.model.Attachment;
import com.study.cancer.model.CommonResult;
import com.study.cancer.service.ApplyService;
import com.study.cancer.service.AttachmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public CommonResult<Object> getAttachmentList(Integer page, Integer rows, String medicalRecordNo, String applyId, String treatmentProcessId) {
        PageHelper.startPage(page, rows);
        CommonResult<Object> result = new CommonResult<>();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("medicalRecordNo", medicalRecordNo);
        map.put("applyId", applyId);
        map.put("treatmentProcessId", treatmentProcessId);
        List<Attachment> attachments = attachmentMapper.selectList(map);
        if (attachments != null && attachments.size() > 0) {
            result.setSuccess(true);
            PageInfo<Attachment> attachmentPageInfo = new PageInfo<>(attachments);
            result.setData(attachmentPageInfo);
        } else {
            result.setMessage("附件获取失败");
        }
        return result;
    }

}
