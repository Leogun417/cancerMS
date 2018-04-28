package com.study.cancer.model;

public class ApplyStateConstant {
    public static final String WAIT_TO_CHECK_DATA = "0";//等待审核材料
    public static final String WAIT_TO_CHECK_CONDITION = "1";//等待评估病情
    public static final String LINE_UP = "2";//正在排队
    public static final String FINISH_LINE_UP = "3";//排队完成
    public static final String TO_HOSPITAL = "4";//入院
    public static final String BREAK_APPOINTMENT = "5";//爽约
    public static final String CAN_NOT_DO_CHEMOTHERAPY = "6";//无法化疗
}
