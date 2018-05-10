package com.study.cancer.service;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.User;

public interface UserService {
    CommonResult showDoctorList(int page, int rows, String username, String phoneNumber, String athorization);

    CommonResult modifyUser(User user);

    CommonResult divideGroup(String[] userIds, String groupId);
}
