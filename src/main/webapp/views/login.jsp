<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>肿瘤患者化疗管理系统-登录</title>
    <link href="${ctxStatic}/themes/easyui/easyui.css" media="screen"
          rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/themes/easyui/icon.css" media="screen"
          rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/themes/easyui/easyui_default.css"
          media="screen" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/themes/login/login.css" rel="stylesheet" type="text/css"/>

    <style type="text/css">
        input {
            height: 25px;
            width: 160px;
            font-size: 15px;
        }

        span {
            font-size: 15px;
        }
    </style>
</head>
<body>
<div class="second_body">
    <form id="loginForm" method="post">
        <div class="logo"></div>
        <div class="title-zh">肿瘤患者化疗管理系统</div>
        <div class="message" data-bind="html:message"></div>

        <table border="0" style="width:300px;">
            <tr>
                <td style="white-space:nowrap; padding-bottom: 5px;width:55px;"><span>用户名：</span></td>
                <td colspan="2"><input required="true"
                                       data-options="prompt: '输入手机号登录',iconCls:'icon-man',validType:'isMobile'"
                                       class="easyui-textbox" type="text" name="phoneNumber" id="phoneNumber" class="login"/>
                </td>
            </tr>
            <tr>
                <td class="lable" style="white-space:nowrap; letter-spacing: 0.5em; vertical-align: middle"><span>密码：</span></td>
                <td colspan="2"><input required="true" data-options="prompt: '输入密码',showEye: 'true',validType:'isPassword'"
                                       class="easyui-passwordbox" name="password" id="password"
                                       class="login"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td colspan="2"><input style="width: 10px" type="checkbox" data-bind="checked:form.remember"/>系统记住我</td>
            </tr>
            <tr>
                <td colspan="3" style="text-align:center">
                    <input type="submit" value="登${message}录" class="login_button"/>
                    <input type="reset" value="注册" onclick="reg()" class="reset_botton"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="win" class="easyui-window" closed="true" title="用户注册" style="display:none;width:800px;height:290px;">
    <iframe name="regWin" width="100%" height="100%" frameborder="0" src="${ctx}/registerPage"></iframe>
</div>

<div id="inputDialog" closed="true" class="easyui-dialog" style="display:none;padding:5px;width:400px;height:200px;"
     title="输入医生码" data-options="iconCls:'icon-ok'" buttons="#dlg-buttons">
    若为本院医生请输入医生码：
    <input type="text" data-options="multiline:false,prompt:'患者直接点击确定即可'" class="easyui-textbox" name="doctorCode"
           id="doctorCode"/>
</div>
<div id="dlg-buttons">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="inputDoctorCode()">确定</a>
</div>
<div id="registerDialog" closed="true" class="easyui-dialog" style="display:none;padding:5px;width:300px;height:200px;"
     title="提示" data-options="iconCls:'icon-ok'" buttons="#dlg-buttons1">
    <span id="prompt" style="font-size: 15px;color: #1a69a4"></span>
</div>
<div id="dlg-buttons1">
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="regWin.window.registerEnd()">确定</a>
</div>
</body>

<<!--  引入layer js-->
<script src="${ctxStatic}/js/jquery/jQuery-2.2.0.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/jquery.easyui.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/easyui-lang-zh_CN.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/idCardNoValidation.js"></script>
<script type="text/javascript">
    function closeRegWin() {
        $('#win').window('close');
    }
    $('#loginForm').form({
        url: '${ctx}/login',
        onSubmit: function () {

            return $(this).form('validate');
        },
        success: function (data) {
            data = $.parseJSON(data)
            if (data.success) {
                ;
                window.location.href = "${ctx}/index";
            } else {
                $.messager.alert('提示', data.message, 'error');
            }
        }
    });
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
        }
    });

    function reg() {
        $("#inputDialog").window('open');
    }

    function inputDoctorCode() {
        var doctorCode = $("#doctorCode").val();
        $.ajax({
            type: 'POST',
            url: "${ctx}/assign",//根据是否有医生码区分病人和医生权限
            dataType: "json",
            data: {"doctorCode": doctorCode},
            async: true,
            success: function (data) {
            }
        });
        $('#win').window('open');
        $('#inputDialog').dialog('close');
    }
</script>
</html>