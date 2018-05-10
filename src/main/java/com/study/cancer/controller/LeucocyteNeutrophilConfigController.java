package com.study.cancer.controller;

import com.study.cancer.model.CommonResult;
import com.study.cancer.model.LeucocyteNeutrophilConfigWithBLOBs;
import com.study.cancer.service.LeucocyteNeutrophilConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/dataConfig")
public class LeucocyteNeutrophilConfigController extends BaseController {
    @Resource
    LeucocyteNeutrophilConfigService leucocyteNeutrophilConfigService;

    @RequestMapping("/show")
    public String show(Model model) {
        CommonResult select = leucocyteNeutrophilConfigService.select();
        if (select.isSuccess()) {
            model.addAttribute("config", select.getData());
        }
        return "/data_suggestion";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(LeucocyteNeutrophilConfigWithBLOBs leucocyteNeutrophilConfigWithBLOBs) {
        CommonResult select = leucocyteNeutrophilConfigService.select();
        if (select.isSuccess()) {
            CommonResult modify = leucocyteNeutrophilConfigService.modify(leucocyteNeutrophilConfigWithBLOBs);
            return modify.getMessage();
        } else {
            CommonResult add = leucocyteNeutrophilConfigService.add(leucocyteNeutrophilConfigWithBLOBs);
            return add.getMessage();
        }
    }
}
