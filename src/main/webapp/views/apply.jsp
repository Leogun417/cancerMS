<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-入院申请</title>
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
            border: none;
            background-color: #f2f2f2;
        }

        .userInfo .cancer-group select {
            height: 25px;
            width: 60px;
            margin-left: 15px;
            border: none;
            appearance: none;
            -moz-appearance: none; /* Firefox */
            -webkit-appearance: none; /* Safari 和 Chrome */;
            background-color: #f2f2f2;
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
<h3 align="center" style="margin-top:10px;margin-bottom: 10px">入院申请单</h3>
<div class="datagrid-toolbar"></div>
<div>
    <div style="padding-top: 10px" class="row">
        <form method="post" id="applyForm" enctype="multipart/form-data">
            <div class="userInfo">
                <div class="row ">
                    <div class="col-md-6 col-sm-6">
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>姓　　名:</label>
                            <div>
                                <c:if test="${not empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isChinese'" type="text" name="username"
                                           id="username" value="${loginUser.username}"/>
                                </c:if>
                                <c:if test="${empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isChinese'" type="text" name="username"
                                           id="username" value="${applyInfo.username}"/>
                                </c:if>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>年　　龄:</label>
                            <div>
                                <c:if test="${not empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isAge'" type="text" name="age" id="age"
                                           value="${loginUser.age}"/>
                                </c:if>
                                <c:if test="${empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isAge'" type="text" name="age" id="age"
                                           value="${applyInfo.age}"/>
                                </c:if>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>手机号码:</label>
                            <div>
                                <c:if test="${not empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isMobile'" type="text" name="phoneNumber"
                                           id="phoneNumber" value="${loginUser.phoneNumber}"/>
                                </c:if>
                                <c:if test="${empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isMobile'" type="text" name="phoneNumber"
                                           id="phoneNumber" value="${applyInfo.phoneNumber}"/>
                                </c:if>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>性　　别:</label>
                            <div>
                                <c:if test="${not empty edit}">
                                    <select disabled class="easyui-validatebox" required="true" name="sex" id="sex">
                                        <c:if test="${loginUser.sex eq '男'}">
                                            <option value="男" selected>男</option>
                                            <option value="女">女</option>
                                        </c:if>
                                        <c:if test="${loginUser.sex eq '女'}">
                                            <option value="男">男</option>
                                            <option value="女" selected>女</option>
                                        </c:if>
                                    </select>
                                </c:if>
                                <c:if test="${empty edit}">
                                    <select disabled class="easyui-validatebox" required="true" name="sex" id="sex">
                                        <c:if test="${applyInfo.sex eq '男'}">
                                            <option value="男" selected>男</option>
                                            <option value="女">女</option>
                                        </c:if>
                                        <c:if test="${applyInfo.sex eq '女'}">
                                            <option value="男">男</option>
                                            <option value="女" selected>女</option>
                                        </c:if>
                                    </select>
                                </c:if>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>证件号码:</label>
                            <div>
                                <c:if test="${not empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isIdCard'" type="text" name="idCardNo"
                                           id="idCardNo" value="${loginUser.idCardNo}"/>
                                </c:if>
                                <c:if test="${empty edit}">
                                    <input readonly class="easyui-validatebox" required="true"
                                           data-options="validType:'isIdCard'" type="text" name="idCardNo"
                                           id="idCardNo" value="${applyInfo.idCardNo}"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="datagrid-toolbar"></div>
            <c:if test="${not empty edit}">
                <a style="color:#0E2D5F;float: right" href="javascript:void(0);" onclick="toUserInfo()">个人信息有误？点击修改</a>
            </c:if>
            <div class="row" style="margin-top: 50px">
                <div class="col-md-6 col-sm-6">
                    <div class="cancer-group row">
                        <label class="pull-left label-lg"><span>*</span>白细胞浓度:</label>
                        <div>
                            <input value="${applyInfo.leucocyteConcentration}"
                                   style="font-size:15px;height: 25px;width: 160px;margin-left: 15px;background-color: #f2f2f2;"
                                   class="easyui-textbox" required="true"
                                   type="text" name="leucocyteConcentration"
                                   id="leucocyteConcentration"/>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6">
                    <div class="cancer-group row">
                        <label class="pull-left label-lg"><span>*</span>中性粒细胞浓度:</label>
                        <div>
                            <input value="${applyInfo.neutrophilConcentration}"
                                   style="font-size:15px;height: 25px;width: 160px;margin-left: 15px;background-color: #f2f2f2;"
                                   class="easyui-textbox" required="true"
                                   type="text" name="neutrophilConcentration"
                                   id="neutrophilConcentration"/>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 col-sm-12">
                    <div class="cancer-group row">
                        <label class="pull-left label-lg">备　　注:</label>
                        <textarea name="remark" id="remark"
                                  style="font-size:15px;margin-left:25px;height: 180px;width: 42%;border: solid 1px #626262;background-color: #f2f2f2;">${applyInfo.remark}</textarea>
                    </div>
                </div>
            </div>

            <div class="row" style="margin-bottom: 50px">
                <div class="col-md-12 col-sm-12">
                    <label class="pull-left label-lg"
                           style="font-size:15px;margin-right: 15px"><span>*</span>检查材料:</label>
                    <c:if test="${not empty edit}">
                        <table id="fileTable">
                            <tbody>
                            <tr id="tr0">

                                <td>
                                    <input id="file0" style="height:30px;width: 260px;" name="file0">
                                </td>
                                <td>
                                    <a style="margin-left: 15px" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-add'" href="javascript:void(0);" onclick="addRow()">新增</a>
                                </td>
                                <td>
                                    <a style="margin-left: 15px" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-reload'" href="javascript:void(0);"
                                       onclick="delFile('file0')">清空</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty edit}">
                        <table id="dtList" toolbar="#tb"></table>
                    </c:if>

                </div>

            </div>
            <div style="z-index: 9999; position: fixed ! important; right: 0px; bottom: 0px;background-color: rgba(98,98,98,0.3);width: 100%;height:35px">
                <c:if test="${not empty edit}">
                    <input type="submit" class="btn btn-primary"
                           style="margin-left: 45%;" value="提交"/>
                </c:if>
                <c:if test="${empty edit}">
                    <c:if test="${applyInfo.state eq 0 or applyInfo.state eq 1}">
                        <button onclick="sendMsgForFile()" type="button" class="btn btn-primary"
                                style="margin-left: 40%;">要求追加材料
                        </button>
                        <button onclick="openJuge()" type="button" class="btn btn-primary"
                                style="margin-left: 15px;">危重度评定
                        </button>
                    </c:if>
                </c:if>
            </div>

        </form>
    </div>
    <div id="jugeDialog" closed="true" class="easyui-dialog" style="display:none;padding:5px;width:400px;height:200px;"
         title="危重度评定" data-options="iconCls:'icon-ok'" buttons="#dlg-buttons">
        <div style="width:90%;word-wrap:break-word;margin:5px;font-size: 15px">请对病人病情危重度进行评定，评定结果将影响到入院顺序：</div>
        <select readonly="" id="jugeResult" style="margin-left:5px;font-size:15px;width: 120px">
            <option value="1">
                轻微
            </option>
            <option value="2">
                较重
            </option>
            <option value="3">
                严重
            </option>
            <option value="4">
                危急
            </option>
        </select>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="juge()">确定</a>
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
    <c:if test="${empty edit}">
    $('input').each(function () {
        $(this).attr("required", false);
        $(this).attr("readonly", "readonly");
    });
    $('select').each(function () {
        $(this).attr("readonly", "readonly");
    });
    $('textarea').each(function () {
        $(this).attr("readonly", "readonly");
    });
    </c:if>


    var num = 0;

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
            '                                <a id="delFile' + num + '" style="margin-left: 15px" href="javascript:void(0);" onclick="delFile(\'file' + num + '\')">清空</a>' +
            '                            </td>' +
            '                            <td>' +
            '                                <a id="delRow' + num + '" style="margin-left: 15px" href="javascript:void(0);" onclick="delRow(\'tr' + num + '\')">删除</a>' +
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

    <c:if test="${empty edit}">

    function openJuge() {
        $("#jugeDialog").window("open");
    }

    function juge() {
        var medicalRecordNo =
        ${applyInfo.medicalRecordNo}
        var applyId = ${applyInfo.id}
            $.ajax({
                type: 'POST',
                url: '${ctx}/apply/juge',
                dataType: "text",
                data: {
                    "medicalRecordNo": medicalRecordNo,
                    "applyId": applyId,
                    "level": $("#jugeResult").val()
                },
                async: true,
                success: function (data) {
                    $.messager.alert('提示', data, 'info', function () {
                        $("#jugeDialog").window("close");
                    });
                }
            });
    }

    function sendMsgForFile() {
        var patientId = ${applyInfo.patientId};
        $.messager.prompt('提示', '请输入需要病人追加的材料', function (val) {
            if (val) {
                $.ajax({
                    type: 'POST',
                    url: "${ctx}/apply/sendForFile",
                    dataType: "text",
                    data: {
                        "patientId": patientId,
                        "msg": val
                    },
                    async: true,
                    success: function (data) {

                    }
                });
            }
        });
    }

    </c:if>

    function toUserInfo() {
        parent.Open("用户信息", '/userInfo');
    }


    $('#dtList').datagrid({
        url: '${ctx}/apply/getAttachments',
        idField: 'id',
        title: '',
        width: $("#remark").width(),
        fit: false,
        loadMsg: '数据加载中...',
        rownumbers: true,
        singleSelect: true,
        pagination: true,
        pageSize: 5,
        pageList: [5, 10, 15],
        fitColumns: true,
        nowrap: true,
        queryParams: {
            applyId: function () {
                return ${applyInfo.id};
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
                    field: 'attachmentName',
                    title: '资料名称',
                    width: 80
                },
                {
                    field: 'action',
                    title: '操作',
                    width: 40,
                    formatter: function (value, row, index) {
                        return "<a class='down' href='javascript:void(0);' class='easyui-linkbutton' onclick=\"downFile('" + row.attachmentName + "',${applyInfo.medicalRecordNo})\"></a>"
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $('.down').linkbutton({text: '下载', plain: true, iconCls: 'icon-xiazai'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

    function downFile(attachmentName, medicalRecordNo) {
        window.location.href = "${ctx}/download?fileName=" + attachmentName + "&medicalRecordNo=" + medicalRecordNo;
    }

    $(function () {
        $('#file0').filebox({
            required: true,
            buttonText: '选择文件',
            prompt: '选择文件',
            buttonAlign: 'right'
        })
    })


    $('#applyForm').form({
        url: '${ctx}/apply/add',
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