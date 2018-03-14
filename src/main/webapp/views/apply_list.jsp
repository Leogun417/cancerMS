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
            width: 160px;
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
    <div id="win" class="easyui-window userInfo" closed="true" title="追加材料"
         style="display:none;width:500px;height:200px;">
        <form method="post" id="fileForm" enctype="multipart/form-data">
            <input type="hidden" name="applyId" id="applyId"/>
            <input type="hidden" name="medicalRecordNo" id="medicalRecordNo"/>
            <table class="row" id="fileTable" style="margin-top: 10px;margin-left: 15px">
                <tbody>
                <tr id="tr0">
                    <td>
                        <input id="file0" style="height:30px;width: 260px;" name="file0">
                    </td>
                    <td>
                        <a style="margin-left: 15px" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                           href="javascript:void(0);" onclick="addRow()">新增</a>
                    </td>
                    <td>
                        <a style="margin-left: 10px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'"
                           href="javascript:void(0);" onclick="delFile('file0')">清空</a>
                    </td>
                </tr>
                </tbody>
            </table>
            <input type="submit" style="margin-top:60px;width: auto;height: auto" class="row btn btn-primary" value="提交"/>
        </form>
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

    var num = 0;

    $('#fileForm').form({
        url: '${ctx}/apply/addFiles',
        onSubmit: function () {
            var row = $('#dtList').datagrid('getSelected');
            var selectedId = row.id;
            var selectedMedicalNo = row.medicalRecordNo;
            $("#applyId").val(selectedId);
            $("#medicalRecordNo").val(selectedMedicalNo);
            return $(this).form('validate');
        },
        success: function (data) {
            $.messager.confirm('提示', data, function () {
                $("#win").window('close');
            });
        }
    });


    $(function () {
        $('#file0').filebox({
            required: true,
            buttonText: '选择文件',
            prompt: '选择文件',
            buttonAlign: 'right'
        })

    })

    function delRow(id) {
        $("#" + id).remove();
        num--;
    }

    function addRow() {
        num++;
        var tbody = $("#fileTable tbody");
        var newTr = '<tr id="tr' + num + '">' +
            '                            <td>' +
            '                                <input id="file' + num + '" style="height:30px;width: 260px;" name="file' + num + '">' +
            '                            </td>' +
            '                            <td>' +
            '                                <a id="delFile' + num + '" style="margin-left: 10px" href="javascript:void(0);" onclick="delFile(\'file' + num + '\')">清空</a>' +
            '                            </td>' +
            '                            <td>' +
            '                                <a id="delRow' + num + '" style="margin-left: 10px" href="javascript:void(0);" onclick="delRow(\'tr' + num + '\')">删除</a>' +
            '                            </td>' +
            '                        </tr>';
        tbody.append(newTr);
        $('#file' + num).filebox({
            required: true,
            buttonText: '选择文件',
            prompt: '选择文件',
            buttonAlign: 'right'
        })
        $('#addRow' + num).linkbutton({
            iconCls: 'icon-add'
        });
        $('#delFile' + num).linkbutton({
            iconCls: 'icon-reload'
        });
        $('#delRow' + num).linkbutton({
            iconCls: 'icon-remove'
        });
    }

    function delFile(id) {
        $('#' + id).filebox('clear');
    }

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
        url: '${ctx}/apply/getSelfList',
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
                        if (row.state == 0 || row.state == 1) {
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
                        return "<a class='editcls' class='easyui-linkbutton' onclick='addFile()'></a>"
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function(){
                $(this).attr("title",$(this).text());
            });
            $('.editcls').linkbutton({text: '追加材料', plain: true, iconCls: 'icon-add'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

    function addFile() {
        $("#win").window('open');
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