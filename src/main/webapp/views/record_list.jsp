<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-就诊记录</title>

    <link href="${ctxStatic}/themes/easyui/easyui.css" media="screen"
          rel="stylesheet" type="text/css"/>

    <link href="${ctxStatic}/themes/easyui/icon.css" media="screen"
          media="screen" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${ctxStatic}/js/bootstrap/css/bootstrap.min.css">
    <link href="${ctxStatic}/themes/easyui/easyui_default.css"
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

        .cancer-group {
            width: 100%;
            margin-bottom: 20px;
        }

        .cancer-group label {
            font-size: 15px;
        }

        .userInfo .cancer-group input {
            width: 120px;
            font-size: 15px;
            margin-left: 15px;
        }

        .userInfo .cancer-group select {
            margin-left: 15px;
        }
    </style>

</head>

<body style="background-color: #f2f2f2">

<div class="easyui-layout" fit="true">
    <div id="content" region="center" style="padding: 0px;border:0px">
        <table width="100%" id="dtList" toolbar="#tb"></table>
        <div id="tb" style="margin:5px;height:auto">
            <div style="margin-bottom: 5px">
                <c:if test="${loginUser.athorization ne 1}">
                    <span>患者姓名:</span>
                    <input class="easyui-textbox" id="patientName">
                    <span>手机号：</span>
                    <input class="easyui-textbox" id="phoneNumber">
                </c:if>
                <span>就诊状态：</span>
                <select editable="false" style="width: 120px" class="easyui-combobox" id="state">
                    <option value="">全部</option>
                    <option value="0">就诊过程中</option>
                    <option value="1">结束治疗</option>
                </select>
                <span>就诊号：</span>
                <input class="easyui-textbox" id="medicalRecordNo">
                <a style="margin-left: 15px" id="search" href="javascript:void(0);" class="easyui-linkbutton"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
    </div>
</div>

<div id="win" class="easyui-window userInfo" closed="true" title="离院"
     style="display:none;width:400px;height:300px;">
    <form id="leaveHospitalForm" style="margin-top: 15px;margin-left: 10px">
        <input type="hidden" name="medicalRecordNo" id="medicalRecordNoOfWin"/>
        <div class="cancer-group row">
            <label class="pull-left label-lg"><span>*</span>就诊状态:　　　</label>

            <select id="stateOfWin" data-options="validType:'selectValidate[\'#stateOfWin\'] '" name="state"
                    editable="false" style="height:25px;width: 120px;margin-bottom: 10px"
                    class="easyui-combobox row">
                <option value="">请选择</option>
                <option value="0">继续治疗</option>
                <option value="1">完成出院</option>
            </select>
        </div>
        <div id="continueDiv">
            <div class="cancer-group row" id="nextDateDiv">
                <label class="pull-left label-lg"><span>*</span>下次入院时间:　</label>
                <div>
                    <input required="true" class="easyui-textbox" id="nextToHospitalDate" name="nextToHospitalDate">
                </div>
            </div>
            <div class="cancer-group row">
                <label class="pull-left label-lg"><span>*</span>白细胞水平:　　</label>
                <div>
                    <input required="true" name="leucocyteConcentration" class="easyui-textbox row" style="height: 25px"
                           id="leucocyteConcentration">
                </div>
            </div>
            <div class="cancer-group row">
                <label class="pull-left label-lg"><span>*</span>中性粒细胞水平:</label>
                <div>
                    <input required="true" name="neutrophilConcentration" class="easyui-textbox row"
                           style="height: 25px"
                           id="neutrophilConcentration">
                </div>
            </div>
        </div>
        <div class="row">
            <input type="submit" style="background-color: #0984ff;color: white" class="easyui-linkbutton" value="确认"/>
        </div>
    </form>
</div>
<div id="win2" class="easyui-window userInfo" closed="true" title="离院"
     style="display:none;width:400px;height:230px;">
    <form id="leaveDataForm" style="margin-top: 15px;margin-left: 10px">
        <input type="hidden" name="medicalRecordNo" id="medicalRecordNoOfWin2"/>
        <div class="cancer-group row">
            <label class="pull-left label-lg"><span>*</span>白细胞水平:　　</label>
            <div>
                <input required="true" name="leucocyteConcentration" class="easyui-textbox row" style="height: 25px"
                       id="leaveLeucocyteConcentration">
            </div>
        </div>
        <div class="cancer-group row">
            <label class="pull-left label-lg"><span>*</span>中性粒细胞水平:</label>
            <div>
                <input required="true" name="neutrophilConcentration" class="easyui-textbox row"
                       style="height: 25px"
                       id="leaveNeutrophilConcentration">
            </div>
        </div>
        <div class="row">
            <input type="submit" style="background-color: #0984ff;color: white" class="easyui-linkbutton" value="确认"/>
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
    $("#search").click(function () {
        $('#dtList').datagrid('reload');
    });
    $("#stateOfWin").combobox({
        onSelect: function (record) {
            if (record.value == "1") {
                $("#continueDiv").hide();
            } else {
                $("#continueDiv").show();
            }
        }
    })
    $('#dtList').datagrid({
        url: '${ctx}/record/showList',
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
                return $("#state").val();
            },
            patientName: function () {
                return $("#patientName").val()
            },
            phoneNumber: function () {
                return $("#phoneNumber").val()
            },
            medicalRecordNo: function () {
                return $("#medicalRecordNo").val()
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
                    title: '就诊号',
                    width: 20
                },
                {
                    field: 'username',
                    title: '姓名',
                    width: 20
                },
                {
                    field: 'sex',
                    title: '性别',
                    width: 20
                },
                {
                    field: 'age',
                    title: '年龄',
                    width: 20
                },
                {
                    field: 'phoneNumber',
                    title: '手机号',
                    width: 40
                },
                {
                    field: 'severity',
                    title: '病情',
                    width: 20,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return "轻微";
                        } else if (value == '1') {
                            return "较重";
                        } else if (value == '2') {
                            return "严重";
                        } else if (value == "3") {
                            return "危急";
                        } else {
                            return "等待评估";
                        }

                    }
                },
                {
                    field: 'treatmentPlan',
                    title: '治疗方案',
                    width: 40,
                    formatter: function (value, row, index) {
                        if (value == null || "" == value) {
                            return "方案暂未制定";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'toHospitalTimes',
                    title: '入院次数',
                    width: 30,
                    formatter: function (value, row, index) {
                        if (value == null || "" == value) {
                            return "暂未入院";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'isInHospital',
                    title: '是否在院',
                    width: 30,
                    formatter: function (value, row, index) {
                        if (value == "no") {
                            return "已离院";
                        } else {
                            return "在院";
                        }
                    }
                },
                {
                    field: 'action',
                    title: '操作',
                    width: 60,
                    formatter: function (value, row, index) {
                        var content = "<a class='easyui-linkbutton showProcesscls' onclick=\"showProcessList('" + row.id + "')\"></a>";
                        <c:if test="${loginUser.athorization eq 2}">
                        /*治疗组医生可以标记患者离院 修改稿下次入院时间*/
                        if (row.isInHospital == "yes") {
                            content = content + "<a class='easyui-linkbutton leavecls' onclick=\"leaveHospital('" + row.id + "')\"></a>";
                        } else if (row.state == "0") {
                            content = content + "<a class='easyui-linkbutton nextTimecls' onclick=\"modifyNextTime('" + row.id + "')\"></a>";
                        }
                        </c:if>
                        <c:if test="${loginUser.athorization eq 1}">
                        if (row.isInHospital == "no" && row.state == "0") {
                            content = content + "<a class='easyui-linkbutton leavedatacls' onclick=\"submitLeaveData('" + row.id + "')\"></a>" +
                                "<a class='easyui-linkbutton againcls' onclick=\"againToHospital('" + row.id + "')\"></a>";
                        }
                        </c:if>
                        return "<div style='white-space:pre-wrap;word-wrap:break-word;'>" + content + "</div>";
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
            $('.showProcesscls').linkbutton({text: '查看治疗过程', plain: true, iconCls: 'icon-dakai'});
            $('.nextTimecls').linkbutton({text: '下次入院时间', plain: true, iconCls: 'icon-dakai'});
            $('.leavecls').linkbutton({text: '离院', plain: true, iconCls: 'icon-dakai'});
            $('.leavedatacls').linkbutton({text: '提交材料', plain: true, iconCls: 'icon-dakai'});
            $('.againcls').linkbutton({text: '二次入院申请', plain: true, iconCls: 'icon-dakai'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

    $("#nextToHospitalDate").datebox({
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

    $('#leaveHospitalForm').form({
        url: "${ctx}/record/leave",
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            $.messager.alert('提示', data, 'info', function () {
                window.location.reload();
                $("#win").window("close");
            });
        }
    });

    $('#leaveDataForm').form({
        url: "${ctx}/record/leaveData",
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            $.messager.alert('提示', data, 'info', function () {
                $("#win2").window("close");
            });
        }
    });

    function againToHospital(medicalRecord) {
        parent.Open("提交二次入院申请", '/apply/fillIn?medicalRecordNo=' + medicalRecord);
    }

    function submitLeaveData(medicalRecord) {
        $("#medicalRecordNoOfWin2").val(medicalRecord);
        $("#win2").window("open");
    }

    function leaveHospital(medicalRecordNo) {
        $("#medicalRecordNoOfWin").val(medicalRecordNo);
        $("#win").window("open");
    }

    function modifyNextTime() {
        //todo 修改下次入院时间
    }

    function showProcessList(medicalRecordNo) {
        parent.Open(medicalRecordNo + "号就诊过程", '/process/processList?medicalRecordNo=' + medicalRecordNo);
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