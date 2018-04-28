<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-用户信息</title>
    <style type="text/css">
        .cancer-group {
            width: 100%;
            margin-bottom: 20px;
        }

        .cancer-group label {
            font-size: 15px;
        }

        .userInfo .cancer-group input {
            height: 25px;
            width: 160px;
            font-size: 15px;
            margin-left: 15px;
        }

        .userInfo .cancer-group select {
            height: 25px;
            width: 60px;
            margin-left: 15px;
        }

        td {
            height: 40px;
        }
    </style>
    <link rel="stylesheet" href="${ctxStatic}/js/bootstrap/css/bootstrap.min.css">
    <link href="${ctxStatic}/themes/easyui/easyui.css" media="screen"
          rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/themes/easyui/easyui_default.css"
          media="screen" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/themes/easyui/icon.css" media="screen"
          media="screen" rel="stylesheet" type="text/css">
</head>

<body style="background-color: #f2f2f2">
<h3 align="center" style="margin-top:10px;margin-bottom: 10px">个人信息</h3>
<div class="datagrid-toolbar"></div>
<div>
    <div style="padding-top: 10px" class="row">
        <form method="post" id="modifyForm" enctype="multipart/form-data">
            <div class="userInfo">
                <div class="row ">
                    <div class="col-md-6 col-sm-6">
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>姓　　名:</label>
                            <div>
                                <input type="hidden" name="id" value="${loginUser.id}"/>
                                <input class="easyui-textbox" required="true"
                                       data-options="validType:'isChinese'" type="text" name="username"
                                       id="username" value="${loginUser.username}"/>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>年　　龄:</label>
                            <div>
                                <input class="easyui-textbox" required="true"
                                       data-options="validType:'isAge'" type="text" name="age" id="age"
                                       value="${loginUser.age}"/>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>手机号码:</label>
                            <div>
                                <input class="easyui-textbox" required="true"
                                       data-options="validType:'isMobile'" type="text" name="phoneNumber"
                                       id="phoneNumber" value="${loginUser.phoneNumber}"/>
                                <button type="button" id="send" class="btn btn-success" onclick="sendSMS()"><span
                                        id="sendText">发送动态码</span></button>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>性　　别:</label>
                            <div>
                                <select editable="false" class="easyui-combobox" required="true" name="sex" id="sex">
                                    <c:if test="${loginUser.sex eq '男'}">
                                        <option value="男" selected>男</option>
                                        <option value="女">女</option>
                                    </c:if>
                                    <c:if test="${loginUser.sex eq '女'}">
                                        <option value="男">男</option>
                                        <option value="女" selected>女</option>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>证件号码:</label>
                            <div>
                                <input class="easyui-textbox" required="true"
                                       data-options="validType:'isIdCard'" type="text" name="idCardNo"
                                       id="idCardNo" value="${loginUser.idCardNo}"/>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>动态码:　</label>
                            <div>
                                <input class="easyui-textbox" required="true"
                                       data-options="validType:'isValidateCode'" type="text" name="validateCode"
                                       id="validateCode"/>
                            </div>
                        </div>
                    </div>
                    <div style="z-index: 9999; position: fixed ! important; right: 0px; bottom: 0px;background-color: rgba(98,98,98,0.3);width: 100%;height:35px">
                        <input type="submit" class="btn btn-primary" style="margin-left: 45%" value="确认修改"/>
                        <input type="button" class="btn btn-warning" style="margin-left: 10px" value="修改密码"
                               onclick="modifyPasswordWin()"/>
                    </div>
                </div>
            </div>

        </form>
        <div id="win" class="easyui-window userInfo" closed="true" title="修改密码"
             style="display:none;width:500px;height:200px;">
            <form id="pwdForm" style="margin-top: 15px;margin-left: 10px">
                <div class="cancer-group row">
                    <label class="pull-left label-lg"><span>*</span>新的密码:</label>
                    <div>
                        <input type="hidden" name="id" value="${loginUser.id}"/>
                        <input style="border: solid 1px black" class="easyui-passwordbox"
                               required="true" data-options="validType:'isPassword'" name="password" id="password"/>
                    </div>
                </div>
                <div class="cancer-group row">
                    <label class="pull-left label-lg"><span>*</span>再次输入:</label>
                    <div>
                        <input style="border: solid 1px black" class="easyui-passwordbox"
                               required="true" data-options="validType:'equals[\'#password\']'" name="passwordAgain"
                               id="passwordAgain"/>
                    </div>
                </div>
                <div class="row">
                    <input type="submit" class="btn btn-primary" value="确认"/>
                </div>
            </form>
        </div>
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
    var time = 60;

    function sendSMS() {
        var rules = $.fn.validatebox.defaults.rules;
        if ($("#phoneNumber").val().trim() == "") {
            $.messager.alert('提示', "请输入手机号！", 'error');
            return;
        }
        if (!rules.isMobile.validator($("#phoneNumber").val())) {
            return;
        }
        $("#sendText").text("请等待" + time + "s");
        $("#send").attr("disabled", true);
        var fn = function () {
            if (time == 0) {
                $("#send").attr("disabled", false);
                $("#sendText").text("重新发送");
                time = 60;
                clearInterval(timer);
                return;
            }
            time--;
            $("#sendText").text("请等待" + time + "s");
        }
        timer = setInterval(fn, 1000);
        $.ajax({
            type: 'POST',
            url: "${ctx}/sendValidateCode",
            dataType: "text",
            data: {
                "phoneNumber": $("#phoneNumber").val(),
                "isRegister": 0
            },
            async: true,
            success: function (data) {

            }
        });
    }

    function modifyPasswordWin() {
        $('#win').window('open');
    }


    $('#pwdForm').form({
        url: '${ctx}/modifyUserInfo',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            $.messager.alert('提示', data, 'info');
        }
    });

    $('#modifyForm').form({
        url: '${ctx}/modifyUserInfo',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            $.messager.alert('提示', data, 'info');
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
        },
        isValidateCode: {
            validator: function (value) {
                var b = false;
                $.ajax({
                    type: 'POST',
                    url: "${ctx}/checkValidateCode",//根据是否有医生码区分病人和医生权限
                    dataType: "json",
                    data: {"validateCode": value},
                    async: false,
                    success: function (data) {
                        b = data;
                    }
                });
                return b;
            },
            message: '动态码不匹配'
        }
    });
</script>
</body>
</html>