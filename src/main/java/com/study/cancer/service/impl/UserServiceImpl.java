package com.study.cancer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.cancer.dao.UserMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.User;
import com.study.cancer.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Override
    public CommonResult showDoctorList(int page, int rows, String username, String phoneNumber, String athorization) {
        PageHelper.startPage(page, rows);
        CommonResult<Object> result = new CommonResult<>();
        Map map = new HashMap();
        map.put("username", username);
        map.put("phoneNumber", phoneNumber);
        map.put("athorization", athorization);
        List<User> users = userMapper.selectDoctorList(map);
        result.setData(new PageInfo<>(users));
        if (users != null && users.size() > 0) {
            result.setSuccess(true);
            result.setMessage("成功查询用户");
        } else {
            result.setMessage("没有查到用户");
        }
        return result;
    }

    @Override
    public CommonResult modifyUser(User user) {
        CommonResult<Object> result = new CommonResult<>();
        int update = userMapper.updateByPrimaryKeySelective(user);
        if (update > 0) {
            result.setSuccess(true);
            result.setMessage("修改成功");
        } else {
            result.setMessage("修改失败");
        }
        return result;
    }
}
