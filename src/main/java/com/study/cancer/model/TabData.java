package com.study.cancer.model;

import java.util.HashMap;
import java.util.Map;

public class TabData {
    public static final String SHOW_APPLY_LIST_FOR_SELF = "我的申请";
    public static final String SHOW_APPLY_LIST = "查看申请";
    public static final String SUBMIT_APPLY = "提交入院申请";
    public static final String SHOW_MEDICAL_RECORD = "就诊记录";

    public final static Map<String, String> tabMap = new HashMap<String, String>();

    static {
        tabMap.put(SHOW_APPLY_LIST_FOR_SELF, "/apply/myApplyList");
        tabMap.put(SHOW_APPLY_LIST, "/apply/list");
        tabMap.put(SUBMIT_APPLY, "/apply/fillIn");
        tabMap.put(SHOW_MEDICAL_RECORD, "/record/recordList");
    }
}
