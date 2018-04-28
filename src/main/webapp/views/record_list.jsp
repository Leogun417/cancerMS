<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-就诊记录</title>
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
                <c:if test="${loginUser.athorization ne 1}">
                    <span>患者姓名:</span>
                    <input class="easyui-textbox" id="patientName">
                    <span>手机号：</span>
                    <input class="easyui-textbox" id="phoneNumber">
                </c:if>
                <span>就诊号：</span>
                <input class="easyui-textbox" id="medicalRecordNo">
                <a style="margin-left: 15px" id="search" href="javascript:void(0);" class="easyui-linkbutton"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
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
    $("#search").click(function () {
        $('#dtList').datagrid('reload');
    });
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
                    width: 30
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
                    width: 40,
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
                    field: 'action',
                    title: '操作',
                    width: 50,
                    formatter: function (value, row, index) {
                        return "<a class='editcls' class='easyui-linkbutton' onclick=\"showProcessList('" + row.id + "')\"></a>"
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
            $('.editcls').linkbutton({text: '查看治疗过程', plain: true, iconCls: 'icon-dakai'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

    function showProcessList(medicalRecordNo) {
        parent.Open(medicalRecordNo + "号就诊过程", '/process/processList?medicalRecordNo=' + medicalRecordNo);
    }
</script>
</body>
</html>