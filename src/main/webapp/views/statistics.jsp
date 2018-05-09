<%--
  Created by IntelliJ IDEA.
  User: Leogun
  Date: 2018/5/8
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<html>
<head>
    <title>统计信息</title>
    <link rel="stylesheet" href="${ctxStatic}/js/bootstrap/css/bootstrap.min.css">
    <link href="${ctxStatic}/themes/easyui/easyui.css" media="screen"
          rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/themes/easyui/easyui_default.css"
          media="screen" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/themes/easyui/icon.css" media="screen"
          media="screen" rel="stylesheet" type="text/css">
</head>
<body>

<div class="row">
    <div class="row" style="margin:15px;height:auto">
        <div style="margin-bottom: 5px">
            <span>年份：</span>
            <select editable="false" style="height:25px;width: 120px" class="easyui-combobox" id="year">

            </select>
            <a style="margin-left: 15px" id="search" href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-search'">查询</a>
        </div>
    </div>
    <div id="waitDaysDiv" class="row" style="width: 100%;height:500px;"></div>
    <div id="toHospitalNumDiv" class="row" style="width: 50%;height:300px;"></div>
</div>

</body>
</html>
<script src="${ctxStatic}/js/jquery/jQuery-2.2.0.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/jquery.easyui.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/easyui-lang-zh_CN.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/idCardNoValidation.js"></script>
<script src="${ctxStatic}/js/echarts.js"></script>
<script type="text/javascript">
    var myChart1 = echarts.init(document.getElementById('waitDaysDiv'));
    var myChart2 = echarts.init(document.getElementById('toHospitalNumDiv'));
    var date = new Date();
    var year = date.getFullYear();
    var startYear = year - 5;
    while (startYear <= year) {
        $("#year").append("<option value='" + startYear + "'>" + startYear + "</option>")
        startYear++;
    }
    myChart1.setOption(
        option = {
            title: {
                text: '月入院等待时间与总入院统计'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data: ['病情轻微', '病情较重', '病情严重', '病情危急', '月总入院人数']
            },
            xAxis: [
                {
                    name: '月份',
                    type: 'category',
                    data: [],
                    axisPointer: {
                        type: 'shadow'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '等待天数',
                    min: 0,
                    max: 10,
                    interval: 1,
                    axisLabel: {
                        formatter: '{value} 天'
                    }
                },
                {
                    type: 'value',
                    name: '月总入院人数',
                    min: 0,
                    interval: 5,
                    axisLabel: {
                        formatter: '{value} 人'
                    }
                }
            ]
        }
    )
    myChart1.on('click', function (params) {
        $.ajax({
            type: 'POST',
            url: "${ctx}/apply/getPieData",//根据是否有医生码区分病人和医生权限
            dataType: "json",
            data: {
                "year": $("#year").val(),
                "month": params.name
            },
            async: true,
            success: function (data) {
                myChart2.setOption(
                    option = {
                        series: [
                            {
                                name: '入院数量',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: data.data.one, name: '病情轻微'},
                                    {value: data.data.two, name: '病情较重'},
                                    {value: data.data.tree, name: '病情严重'},
                                    {value: data.data.four, name: '病情危急'}
                                ],
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    }
                )
            }
        });
    });

    $("#search").click(function () {
        myChart1.showLoading();
        $.ajax({
            type: 'POST',
            url: "${ctx}/apply/getStatisticsData",//根据是否有医生码区分病人和医生权限
            dataType: "json",
            data: {"year": $("#year").val()},
            async: true,
            success: function (data) {
                myChart1.hideLoading();
                myChart1.setOption(
                    option = {
                        xAxis: [
                            {
                                data: data.data.monthList
                            }
                        ],
                        series: [
                            {
                                name: '病情轻微',
                                type: 'bar',
                                data: data.data.oneLevel
                            },
                            {
                                name: '病情较重',
                                type: 'bar',
                                data: data.data.twoLevel
                            },
                            {
                                name: '病情严重',
                                type: 'bar',
                                data: data.data.treeLevel
                            },
                            {
                                name: '病情危急',
                                type: 'bar',
                                data: data.data.fourLevel
                            },
                            {
                                name: '月总入院人数',
                                type: 'line',
                                yAxisIndex: 1,
                                data: data.data.monthNumList
                            }
                        ]
                    }
                )
            }
        });
    })
    myChart2.setOption(
        option = {
            title: {
                text: '当月各病情患者入院数量',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['病情轻微', '病情较重', '病情严重', '病情危急']
            }
        }
    )


</script>
