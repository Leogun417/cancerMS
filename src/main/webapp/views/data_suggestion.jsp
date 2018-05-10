<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
    <title>肿瘤患者化疗管理系统-数据标准及建议配置</title>
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
<h3 align="center" style="margin-top:10px;margin-bottom: 10px">数据标准及建议配置</h3>
<div class="datagrid-toolbar"></div>
<div>
    <div style="padding-top: 10px" class="row">
        <form method="post" id="modifyForm" enctype="multipart/form-data">
            <div class="userInfo">
                <div class="row ">
                    <div class="col-md-6 col-sm-6">
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>白细胞标准值:　　</label>
                            <div>
                                <input class="easyui-textbox" required="true" type="text" name="leucocyte"
                                       id="leucocyte" value="${config.leucocyte}"/>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>中性粒细胞标准值:</label>
                            <div>
                                <input class="easyui-textbox" required="true" type="text" name="neutrophil" id="neutrophil"
                                       value="${config.neutrophil}"/>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>白细胞危险值:　　</label>
                            <div>
                                <input class="easyui-textbox" required="true" type="text" name="dangerLeucocyte"
                                       id="dangerLeucocyte" value="${config.dangerLeucocyte}"/>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>白细胞相关建议:　　</label>
                            <div>
                                <input class="easyui-textbox" required="true" type="text" name="leucocyteSuggestion"
                                       id="leucocyteSuggestion" value="${config.leucocyteSuggestion}"/>
                            </div>
                        </div>
                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>中性粒细胞相关建议:</label>
                            <div>
                                <input class="easyui-textbox" required="true" type="text" name="neutrophilSuggestion"
                                       id="neutrophilSuggestion" value="${config.neutrophilSuggestion}"/>
                            </div>
                        </div>

                        <div class="cancer-group row">
                            <label class="pull-left label-lg"><span>*</span>中性粒细胞危险值:　</label>
                            <div>
                                <input class="easyui-textbox" required="true" type="text" name="dangerNeutrophil"
                                       id="dangerNeutrophil" value="${config.dangerNeutrophil}"/>
                            </div>
                        </div>
                    </div>
                    <div style="z-index: 9999; position: fixed ! important; right: 0px; bottom: 0px;background-color: rgba(98,98,98,0.3);width: 100%;height:35px">
                        <input type="submit" class="btn btn-primary" style="margin-left: 45%" value="确认修改"/>
                    </div>
                </div>
            </div>

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
    $('#modifyForm').form({
        url: '${ctx}/dataConfig/add',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            $.messager.alert('提示', data, 'info', function () {
                window.location.reload();
            });
        }
    });
</script>
</body>
</html>