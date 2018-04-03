<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-收件箱</title>
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
                <span>发送者姓名:</span>
                <input class="easyui-textbox" id="sendFromName">
                <span>消息类型：</span>
                <select editable="false" style="width: 120px" class="easyui-combobox" id="type">
                    <option value="">全部</option>
                    <option value="0">执行请求</option>
                    <option value="1">回执消息</option>
                </select>
                <span>消息状态：</span>
                <select editable="false" style="width: 120px" class="easyui-combobox" id="state">
                    <option value="">全部</option>
                    <option value="0">未读</option>
                    <option value="1">已读</option>
                </select>
                <span style="margin-right: 15px;margin-left: 15px">发送日期:</span>
                <span style="margin-right: 15px">从</span>
                <input class="easyui-textbox" id="sendStartDate">
                <span style="margin-left: 15px;margin-right: 15px">到</span>
                <input class="easyui-textbox" id="sendEndDate">
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
    var time = 5
    var timer;
    $(function () {
        var fn = function () {
            if (time != 0) {
                time--;
                return;
            } else {
                time = 5;
                $('#dtList').datagrid('reload');
                clearInterval(timer);
                timer = setInterval(fn, 1000);
            }
        }
        timer = setInterval(fn, 1000);
    })
    $("#search").click(function () {
        if (end_date < start_date) {
            $.messager.alert('提示', '日期输入不合法！', 'error');
            return;
        }
        $('#dtList').datagrid('reload');
    });
    $("#sendStartDate").datebox({
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
    $("#sendEndDate").datebox({
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
        url: '${ctx}/message/getMessageList',
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
            sendFromName: function () {
                return $("#sendFromName").val()
            },
            state: function () {
                return $("#state").val()
            },
            type: function () {
                return $("#type").val()
            },
            sendStartDate: function () {
                return $("#sendStartDate").val()
            },
            sendEndDate: function () {
                return $("#sendEndDate").val()
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
                    field: 'state',
                    title: '状态',
                    width: 40,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "<img src='${ctxStatic}/image/mail.png' style='width:25px;height:25px;'>";
                        }
                        if (value == '0') {
                            return "<img src='${ctxStatic}/image/mail-unread.png' style='width:25px;height:25px;'>";
                        }
                    }
                },
                {
                    field: 'content',
                    title: '消息内容',
                    width: 150
                },
                {
                    field: 'sendFrom',
                    title: '发送者编号',
                    width: 50
                },
                {
                    field: 'sendFromName',
                    title: '发送者姓名',
                    width: 80
                },
                {
                    field: 'messageType',
                    title: '消息类型',
                    width: 80,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return "执行请求";
                        }
                        if (value == '1') {
                            return "回执信息";
                        }
                    }
                },
                {
                    field: 'sendDate',
                    title: '发送时间',
                    width: 120
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
        },
        onClickRow: function (index, row) {
            var data = {"content":row.content,"id":row.id,"sendDate":row.sendDate,"sendFrom":row.sendFrom,"sendTo":row.sendTo,"state":row.state,"tabTitle":row.tabTitle,"type":row.type,"url":row.url};
            parent.showMsg(data);
        },
    })

    function showApply(username, applyId) {
        parent.Open(applyId + "号申请_" + username, '/apply/showApply/' + applyId);
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