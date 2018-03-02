package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.User;

public interface LoginService {
    CommonResult register(User user);
    boolean checkPhone(String phoneNumber);
    CommonResult login(String phoneNumber, String password);
    CommonResult getMenueByLevel(String authorityLevel);
    CommonResult modifyUserInfo(User user);
}
