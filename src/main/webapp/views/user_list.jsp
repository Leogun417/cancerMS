<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-用户列表</title>
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
                <span>用户姓名:</span>
                <input class="easyui-textbox" id="username">
                <span>手机号码:</span>
                <input class="easyui-textbox" id="phoneNumber">
                <span>权限组：</span>
                <select editable="false" style="width: 120px" class="easyui-combobox" id="athorization">
                    <option value="">全部</option>
                    <option value="2">治疗组医生</option>
                    <option value="3">主任医师</option>
                    <option value="4">管理员</option>
                </select>
                <a style="margin-left: 15px" id="search" href="javascript:void(0);" class="easyui-linkbutton"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
    </div>
</div>
<div id="win" class="easyui-window userInfo" closed="true" title="编辑权限"
     style="display:none;width:300px;height:200px;">
    <form id="editForm" style="margin-top: 15px;margin-left: 10px">
        <div class="cancer-group row">
            <input type="hidden" name="id" id="id"/>
            <label class="pull-left label-lg"><span>*</span>权限组:</label>
            <div>
                <select name="athorization" editable="false" style="width: 120px" class="easyui-combobox" data-options="validType:'selectValidate[\'#athorizationOfForm\']'" id="athorizationOfForm">
                    <option value="">请选择</option>
                    <option value="2">治疗组医生</option>
                    <option value="3">主任医师</option>
                    <option value="4">管理员</option>
                </select>
            </div>
        </div>
        <div class="row">
            <input type="submit" style="position: absolute;left: 10px;bottom: 10px;background-color: #0984ff;color: white" class="easyui-linkbutton" value="确认"/>
        </div>
    </form>
</div>
<script src="${ctxStatic}/js/jquery/jQuery-2.2.0.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/jquery.easyui.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/easyui-lang-zh_CN.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/idCardNoValidation.js"></script>
<script type="text/javascript">
    $('#editForm').form({
        url: '${ctx}/user/edit',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            var data = eval("(" + data + ")");
            var message = data.message;
            $.messager.alert('提示', data.message, 'info', function () {
                $("#win").window("close");
                $('#dtList').datagrid('reload');
            });
        }
    });
    $("#search").click(function () {
        $('#dtList').datagrid('reload');
    });
    function edit(userId) {
        $("#id").val(userId);
        $("#win").window('open');
    }
    $('#dtList').datagrid({
        url: '${ctx}/user/list',
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
            username: function () {
                return $("#username").val()
            },
            phoneNumber: function () {
                return $("#phoneNumber").val()
            },
            athorization: function () {
                return $("#athorization").val()
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
                    title: '姓名',
                    width: 50
                },
                {
                    field: 'phoneNumber',
                    title: '手机号',
                    width: 50
                },
                {
                    field: 'athorization',
                    title: '权限组',
                    width: 20,
                    formatter: function (value, row, index) {
                        if (value == '2') {
                            return "治疗组医生";
                        } else if (value == '3') {
                            return "主任医师";
                        } else if (value == '4') {
                            return "管理员";
                        }
                    }
                },
                {
                    field: 'action',
                    title: '操作',
                    width: 20,
                    formatter: function (value, row, index) {
                        var content = "<a class='editcls' class='easyui-linkbutton' onclick=\"edit(" + row.id + ")\"></a>";
                        return content;
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
            $('.editcls').linkbutton({text: '修改权限', plain: true, iconCls: 'icon-edit'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

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