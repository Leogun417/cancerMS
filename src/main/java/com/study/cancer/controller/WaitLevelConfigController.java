package com.study.cancer.controller;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.WaitLevelConfig;
import com.study.cancer.service.WaitLevelConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/waitConfig")
public class WaitLevelConfigController extends BaseController {
    @Resource
    WaitLevelConfigService waitLevelConfigService;

    @RequestMapping("/show")
    public String show(Model model) {
        CommonResult select = waitLevelConfigService.select();
        if (select.isSuccess()) {
            model.addAttribute("config", select.getData());
        }
        return "/wait_config";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(WaitLevelConfig waitLevelConfig) {
        CommonResult select = waitLevelConfigService.select();
        if (select.isSuccess()) {
            CommonResult modify = waitLevelConfigService.modify(waitLevelConfig);
            return modify.getMessage();
        } else {
            CommonResult add = waitLevelConfigService.add(waitLevelConfig);
            return add.getMessage();
        }
    }
}
