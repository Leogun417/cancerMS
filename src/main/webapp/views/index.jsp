<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8/>
    <title>肿瘤患者化疗管理系统</title>
    <link href="${ctxStatic}/themes/index/easyui.css"
          media="screen" rel="stylesheet" type="text/css"/>
    <style>
        .west {
            width: 200px;
            padding: 10px;
        }

        .north {
            height: 80px;
        }

        .north span {
            color: white;
            font-size: 15px
        }

        .north a {
            text-decoration: none;
        }
    </style>
</head>
<body class="easyui-layout">
<div region="north" class="north" border="false" style="background-color: #339999">
    <div style="margin-top: 20px;margin-left:10px;margin-right: 50px">
        <h1 style="display: inline;font-size: 26px;color: white">肿瘤患者化疗管理系统</h1>
        <div class="pf-user-photo" style="float: right" onMouseOver="show();" onMouseOut="hide()">
            <div id="userInfo" style="display:none;">
                <span>
                    <a href="javascript:void(0);" onclick="Open('用户信息','/userInfo');">
                        <span>用户信息</span>
                    </a>
                </span>
                <span style="display: block">
                    <a href="javascript:void(0)" onclick="logout()">
                        <span>退出登录</span>
                    </a>
                </span>
            </div>
            <div id="userPhoto">
                <span style="margin-right: 20px;margin-top: 0px">欢迎登录，${loginUser.username}</span>
                <img src="${ctxStatic}/themes/images/user.png" style=";width: 40px;height: 40px">
            </div>
        </div>
    </div>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页"
             style="background: rgb(231,229,242)">
            <img src="${ctxStatic}/themes/login/images/index_bg.png" style="height: 99%;width: 90%"/>
        </div>
    </div>
</div>
<div region="west" class="west" title="菜单" style="background-color: #339999">
    <ul id="tree"></ul>
</div>

<div id="tabsMenu" class="easyui-menu" style="width:120px;">
    <div name="close">关闭</div>
    <div name="Other">关闭其他</div>
    <div name="All">关闭所有</div>
</div>

</body>
<script src="${ctxStatic}/js/jquery/jQuery-2.2.0.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/jquery.easyui.min.js"
        type="text/javascript" charset="UTF-8"></script>
<script src="${ctxStatic}/js/easyui/easyui-lang-zh_CN.js"
        type="text/javascript" charset="UTF-8"></script>
<script>
    function show(id) {
        $("#userPhoto").hide();
        $("#userInfo").css("display", "inline");
    }

    function hide(id) {
        $("#userInfo").css("display", "none");
        $("#userPhoto").show();
    }

    function logout() {
        window.location.href="${ctx}/logout";
    }

    // 在右边center区域打开菜单，新增tab
    function Open(text, url) {
        if ($("#tabs").tabs('exists', text)) {
            $('#tabs').tabs('select', text);
        } else {
            $('#tabs').tabs('add', {
                title: text,
                closable: true,
                content: '<iframe name="myFrame" width="100%" height="100%" frameborder="0" src="${ctx}' + url + '"></iframe>'
            });
        }
    }
    $(function () {
        // 实例化树形菜单
        $("#tree").tree({
            url: '${ctx}/getMenu',
            loadFilter: function (data) {
                if (data.success) {
                    return data.data;
                } else {
                    $.messager.alert('提示', data.message, 'error');
                }

            },
            lines: true,
            onClick: function (node) {
                if (node.attributes) {
                    Open(node.text, node.attributes.url);
                }
            },
            formatter: function (node) {
                return '<span  style="font-size:large;color: white">' + node.text + '</span>';
            }
        });





        // 绑定tabs的右键菜单
        $("#tabs").tabs({
            onContextMenu: function (e, title) {
                e.preventDefault();//取消点击右键时的默认事件
                $('#tabsMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data("tabTitle", title);
            }
        });

        // 实例化menu的onClick事件
        $("#tabsMenu").menu({
            onClick: function (item) {
                CloseTab(this, item.name);
            }
        });

        // 几个关闭事件的实现
        function CloseTab(menu, type) {
            var curTabTitle = $(menu).data("tabTitle");
            var tabs = $("#tabs");

            if (type === "close") {
                tabs.tabs("close", curTabTitle);
                return;
            }

            var allTabs = tabs.tabs("tabs");
            var closeTabsTitle = [];

            $.each(allTabs, function () {
                var opt = $(this).panel("options");
                if (opt.closable && opt.title != curTabTitle && type === "Other") {
                    closeTabsTitle.push(opt.title);
                } else if (opt.closable && type === "All") {
                    closeTabsTitle.push(opt.title);
                }
            });

            for (var i = 0; i < closeTabsTitle.length; i++) {
                tabs.tabs("close", closeTabsTitle[i]);
            }
        }
    })

</script>
</html>

