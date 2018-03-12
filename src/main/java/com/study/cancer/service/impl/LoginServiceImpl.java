package com.study.cancer.service.impl;

import com.study.cancer.dao.MenueMapper;
import com.study.cancer.dao.UserMapper;
import com.study.cancer.model.CommonResult;
import com.study.cancer.model.Menue;
import com.study.cancer.model.User;
import com.study.cancer.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    UserMapper userMapper;
    @Resource
    MenueMapper menueMapper;

    @Override
    public CommonResult register(User user) {
        CommonResult<Object> result = new CommonResult<>();
        int insert = userMapper.insert(user);
        if (insert > 0) {
            result.setSuccess(true);
            result.setMessage("注册成功");
        } else {
            result.setMessage("注册失败");
        }
        return result;
    }

    @Override
    public boolean checkPhone(String phoneNumber) {
        User user = userMapper.selectByPhone(phoneNumber);
        if (user != null) {
            return false;
        }
        return true;
    }

    @Override
    public CommonResult login(String phoneNumber, String password) {
        CommonResult<Object> result = new CommonResult<>();
        User user = userMapper.selectByPhone(phoneNumber);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                result.setData(user);
                result.setSuccess(true);
            } else {
                result.setMessage("密码输入错误");
                result.setSuccess(false);
            }

        } else {
            result.setMessage("用户名输入错误");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public CommonResult getMenueByLevel(String authorityLevel) {
        CommonResult<Object> result = new CommonResult<>();
        List<Menue> menue = menueMapper.selectByAuthorityLevel(authorityLevel);
        if (menue != null && menue.size() > 0) {
            result.setSuccess(true);
            result.setData(menue);
        } else {
            result.setMessage("获取菜单失败");
        }
        return result;
    }

    @Override
    public CommonResult modifyUserInfo(User user) {
        CommonResult<Object> result = new CommonResult<>();
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("修改成功");
            result.setData(userMapper.selectByPrimaryKey(user.getId()));
        } else {
            result.setMessage("修改失败");
        }
        return result;
    }
}
