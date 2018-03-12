<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-申请查看</title>
    <style type="text/css">
    </style>
    <link rel="stylesheet" href="${ctxStatic}/js/bootstrap/css/bootstrap.min.css">
    <link href="${ctxStatic}/themes/easyui/easyui.css" media="screen"
          rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/themes/easyui/easyui_default.css"
          media="screen" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/themes/easyui/icon.css" media="screen"
          media="screen" rel="stylesheet" type="text/css">
    <style type="text/css">
        input {
            height: 25px;
            width: 100px;
            font-size: 15px;
            margin-left: 15px;
            background-color: #f2f2f2;
        }

        select {
            height: 25px;
            width: 60px;
            margin-left: 15px;
            background-color: #f2f2f2;
        }

        span {
            font-size: 15px;
        }
    </style>
</head>

<body style="background-color: #f2f2f2">

<div class="easyui-layout" fit="true">
    <div id="content" region="center" style="padding: 0px;border:0px">
        <table width="100%" id="dtList" toolbar="#tb"></table>
        <div id="tb" style="margin:5px;height:auto">
            <div style="margin-bottom: 5px">
                <span>患者姓名:</span>
                <input class="easyui-textbox" id="patientName">
                <span>就诊号:</span>
                <input class="easyui-textbox" id="medicalRecordNo">
                <span>申请单状态：</span>
                <select editable="false" style="width: 120px" class="easyui-combobox" id="state">
                    <option value="">全部</option>
                    <option value="0">等待处理</option>
                    <option value="1">正在排队</option>
                    <option value="2">排队完成</option>
                    <option value="3">已入院</option>
                    <option value="4">爽约</option>
                </select>
                <span style="margin-right: 15px;margin-left: 15px">申请日期:</span>
                <span style="margin-right: 15px">从</span>
                <input class="easyui-textbox" id="applyStartDate">
                <span style="margin-left: 15px;margin-right: 15px">到</span>
                <input class="easyui-textbox" id="applyEndDate">
                <a style="margin-left: 15px" id="search" href="javascript:void(0);" class="easyui-linkbutton"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
    </div>

    <div id="Dialog" closed="true" class="easyui-dialog" style="display:none;padding:5px;width:300px;height:150px;"
         title="提示" data-options="iconCls:'icon-ok'" buttons="#dlg-buttons">
        <span id="prompt" style="font-size: 15px;color: #1a69a4"></span>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="closeDialogAndWin()">确定</a>
    </div>
</div>
<script src="${ctxStatic}/js/jquery/jQuery-2.2.0.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/jquery.easyui.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/easyui-lang-zh_CN.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/idCardNoValidation.js"></script>
<script type="text/javascript">
    var end_date;
    var start_date;

    $("#search").click(function () {
        if (end_date < start_date) {
            $.messager.alert('提示', '日期输入不合法！', 'error');
            return;
        }
        $('#dtList').datagrid('reload');
    });
    $("#applyStartDate").datebox({
        editable: false,
        formatter: function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            if (m < 10)
                m = '0' + m;
            if (d < 10)
                d = '0' + d;
            start_date = y + '-' + m + '-' + d;
            return start_date;
        },
        parser: function (s) {// 配置parser，返回选择的日期
            var t = Date.parse(s);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });
    $("#applyEndDate").datebox({
        editable: false,
        formatter: function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            if (m < 10)
                m = '0' + m;
            if (d < 10)
                d = '0' + d;
            end_date = y + '-' + m + '-' + d;
            return end_date;
        },
        parser: function (s) {// 配置parser，返回选择的日期
            var t = Date.parse(s);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });
    $('#dtList').datagrid({
        url: '${ctx}/apply/getApplyList',
        idField: 'id',
        title: '',
        fit: true,
        loadMsg: '数据加载中...',
        rownumbers: true,
        singleSelect: true,
        pagination: true,
        pageList: [5, 10, 15],
        fitColumns: true,
        nowrap: true,
        queryParams: {
            patientName: function () {
                return $("#patientName").val()
            },
            medicalRecordNo: function () {
                return $("#medicalRecordNo").val()
            },
            state: function () {
                return $("#state").val()
            },
            applyStartDate: function () {
                return $("#applyStartDate").val()
            },
            applyEndDate: function () {
                return $("#applyEndDate").val()
            }
        },
        loadFilter: function (data) {
            var result = new Object();
            result.total = data.total;
            result.rows = data.list;
            return result;
        },
        columns: [
            [
                {
                    field: 'id',
                    title: 'id',
                    width: 20,
                    checkbox: true
                },
                {
                    field: 'username',
                    title: '患者姓名',
                    width: 80
                },
                {
                    field: 'medicalRecordNo',
                    title: '就诊号',
                    width: 80
                },
                {
                    field: 'state',
                    title: '状态',
                    width: 80,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return "等待处理";
                        }
                        if (value == '1') {
                            return "正在排队";
                        }
                        if (value == '2') {
                            return "排队完成";
                        }
                        if (value == '3') {
                            return "已入院";
                        }
                        if (value == '4') {
                            return "<span style='color: red'>爽约</span>";
                        }
                    }
                },
                {
                    field: 'applyDate',
                    title: '申请时间',
                    width: 120
                },
                {
                    field: 'toHospitalDate',
                    title: '入院时间',
                    width: 120,
                    formatter: function (value, row, index) {
                        var res;
                        if (row.state == 0) {
                            res = '等待安排'
                        } else {
                            res = value;
                        }
                        return res;
                    }
                },
                {
                    field: 'action',
                    title: '操作',
                    width: 120,
                    formatter: function (value, row, index) {
                        return "<a class='editcls' class='easyui-linkbutton' onclick=\"showApply('" + row.username + "'," + row.id + ")\"></a>"
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
            $('.editcls').linkbutton({text: '查看', plain: true, iconCls: 'icon-dakai'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

    function showApply(username, applyId) {
        parent.Open(applyId + "号申请_" +　username, '/apply/showApply/' + applyId);
    }

    $.extend($.fn.validatebox.defaults.rules, {
        equals: {
            validator: function (value, param) {
                return value == $(param[0]).val();
            },
            message: '输入不一致'
        },
        isIdCard: {// 验证身份证
            validator: function (value) {
                return idCardNoUtil.checkIdCardNo(value);
            },
            message: '身份证号码格式不正确'
        },
        isMobile: {// 验证手机号码
            validator: function (value) {
                var length = value.length;
                var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[57]{9})|(17[013678]{9})|(15[012356789]{9})$/;
                return length == 11 && mobile.test(value);
            },
            message: '手机号码格式不正确'
        },
        selectValidate: {
            validator: function (value, param) {
                return $(param[0]).find("option:contains('" + value + "')").val() != '';
            },
            message: "该项必选"
        },
        isChinese: {// 验证姓名
            validator: function (value) {
                return /^[\Α-\￥]+$/i.test(value);
            },
            message: '请输入中文'
        },
        isPassword: {// 验证密码
            validator: function (value) {
                return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
            },
            message: '密码不合法（字母开头，允许6-16字节，允许字母数字下划线）'
        },
        isAge: {// 验证年龄
            validator: function (value) {
                return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
            },
            message: '年龄必须是0到120之间的整数'
        },
        isEmailExist: {// 验证email是否存在
            validator: function (value) {
                var rules = $.fn.validatebox.defaults.rules;
                var b = false;
                $.ajax({
                    type: 'POST',
                    url: "${ctx}/checkEmail",//根据是否有医生码区分病人和医生权限
                    dataType: "json",
                    data: {"email": value},
                    async: false,
                    success: function (data) {
                        b = data;
                    }
                });
                rules.isEmailExist.message = 'email已存在';
                if (!rules.email.validator(value)) {
                    rules.isEmailExist.message = rules.email.message;
                    return false;
                }
                return b;
            },
            message: ''
        }
    });
</script>
</body>
</html>