<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-治疗过程</title>
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
        <table width="100%" id="dtList"></table>
    </div>
</div>
<div id="win" class="easyui-window" closed="true" title="附件列表"
     style="display:none;width:500px;height:300px;">
    <table width="100%" id="fileList"></table>
</div>
<script src="${ctxStatic}/js/jquery/jQuery-2.2.0.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/jquery.easyui.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/easyui-lang-zh_CN.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/idCardNoValidation.js"></script>
<script type="text/javascript">
    var processId;

    $("#search").click(function () {
        $('#dtList').datagrid('reload');
    });
    $('#dtList').datagrid({
        url: '${ctx}/process/showList',
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
            medicalRecordNo: function () {
                return ${medicalRecordNo};
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
                    field: 'patientAction',
                    title: '患者行为',
                    width: 80,
                    formatter: function (value, row, index) {
                        if (value == null || value == "") {
                            return "患者无行为"
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'doctorAction',
                    title: '医生行为',
                    width: 80,
                    formatter: function (value, row, index) {
                        if (value == null || value == "") {
                            return "医生无行为"
                        } else {
                            return "<div style='white-space:pre-wrap;word-wrap:break-word;'>" + value + "</div>";
                        }
                    }
                },
                {
                    field: 'doctorName',
                    title: '医生姓名',
                    width: 30
                },
                {
                    field: 'createDate',
                    title: '创建时间',
                    width: 60
                },
                {
                    field: 'action',
                    title: '操作',
                    width: 50,
                    formatter: function (value, row, index) {
                        return "<a class='editcls' class='easyui-linkbutton' onclick=\"showFileList('" + row.id + "')\"></a>"

                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
            $('.editcls').linkbutton({text: '查看附件', plain: true, iconCls: 'icon-dakai'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })
    $('#fileList').datagrid({
        url: '${ctx}/process/showFileList',
        idField: 'id',
        title: '',
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
            processId: function () {
                return processId;
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
                        return "<a class='down' href='javascript:void(0);' class='easyui-linkbutton' onclick=\"downFile('" + row.id + "')\"></a>"
                    }
                }
            ]
        ],
        onLoadSuccess: function (data) {
            $("td").each(function () {
                $(this).attr("title", $(this).text());
            });
            $('.down').linkbutton({text: '下载', plain: true, iconCls: 'icon-xiazai'});
            $("#dtList").datagrid("clearSelections");
            $('#dtList').datagrid('fixRowHeight');
        }
    })

    function downFile(attachmentId) {
        window.location.href = "${ctx}/download?attachmentId=" + attachmentId;
    }

    function showFileList(treatmentProcessId) {
        processId = treatmentProcessId;
        $("#fileList").datagrid("reload");
        $('#win').window('open');
    }

</script>
</body>
</html>