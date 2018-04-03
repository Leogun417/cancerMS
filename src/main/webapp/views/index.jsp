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
            height: 90px;
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
    <%--右下角消息--%>
    <div id="rbmsg"
         style="background:white;position:fixed;right:10px;bottom:10px;width:320px;display:none;border:2px solid #339999;">
        <!-- 声音提示 -->
        <audio id='msgnotify' autoplay="autoplay"></audio>
        <div style="background:#339999;padding:5px 10px 5px 10px;color:white">
            <span id='msgtitile' style="line-height:25px">消息提示</span>
            <button onclick="$('#rbmsg').slideUp()"
                    style="float:right;">&times;
            </button>
        </div>
        <div style="min-height:60px;padding:20px 20px 10px 20px">
            <span id="msgcontent"
                  style="width:100%;white-space:pre-wrap;word-wrap:break-word;font-size: 15px;color: #339999"></span>
        </div>
        <div style="border-top: 1px solid LightGrey;margin:20px 20px 0px 20px;padding:8px 0 8px 0">
            <button id='msgyes' style="font-size:15px;background-color:rgba(0,128,12,0.7);color:#ffffff">确定</button>
        </div>
    </div>
    <div style="margin-top: 20px;margin-left:10px;margin-right: 50px">
        <h1 style="display: inline;font-size: 26px;color: white">肿瘤患者化疗管理系统</h1>
        <div style="float: right" onMouseOver="show();" onMouseOut="hide()">
            <div id="userInfo" style="display:none;">
                <span>
                    <a href="javascript:void(0);" onclick="Open('用户信息','/userInfo');">
                        <span>用户信息</span>
                    </a>
                </span>
                <span style="display: block">
                    <a href="javascript:void(0);" onclick="Open('收件箱','/message/list');">
                        <span>收件箱</span>
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
    var websocket = null;
    var heartCheck = {
        timeout: 80000,//80s
        timeoutObj: null,
        reset: function () {
            clearTimeout(this.timeoutObj);
            this.start();
        },
        start: function () {
            this.timeoutObj = setTimeout(function () {
                websocket.send("heartbeat");
            }, this.timeout)
        }
    }

    function showMsg(data) {
        if (typeof data == "string") {
            var dataObj = eval("(" + data + ")");
        } else {
            var dataObj = data;
        }
        $("#msgyes").click(function () {
            $("#rbmsg").slideUp();
            if (dataObj != null) {
                $.ajax({
                    type: 'get',
                    url: "${ctx}/message/changeState",
                    dataType: "text",
                    data: {
                        "messageId": dataObj.id
                    },
                    async: true,
                    success: function (d) {

                    }
                });
            }
            Open(dataObj.tabTitle, dataObj.url);
        })
        $("#msgcontent").text(dataObj.content);
        $("#rbmsg").slideDown();
    }

    function show(id) {
        $("#userPhoto").hide();
        $("#userInfo").css("display", "inline");
    }

    function hide(id) {
        $("#userInfo").css("display", "none");
        $("#userPhoto").show();
    }

    function logout() {
        window.location.href = "${ctx}/logout";
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
        $.ajax({
            type: 'get',
            url: "${ctx}/message/checkUnreadMsg",
            dataType: "json",
            async: true,
            success: function (data) {
                if (data) {
                    var data = {"content": "有未读消息，请查看", "tabTitle": "收件箱", "url": "/message/list"};
                    showMsg(data);
                }
            }
        });
        //websocket链接
        websocket = connectWebSocket();
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

    function connectWebSocket() {

        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            if (location.protocol == "https:") {
                websocket = new WebSocket("wss://" + location.host + "${ctx}/webSocketServer");
            } else {
                websocket = new WebSocket("ws://" + location.host + "${ctx}/webSocketServer");
            }

        } else {
            $.messager.confirm('提示', "您的浏览器不支持websocket!将无法收到消息通知！请更换新版本浏览器");
            websocket = new SockJS("http://" + location.host + "${ctx}/sockjs/webSocketServer");
        }
        websocket.onerror = function () {
            $.messager.confirm('提示', "websocket未能连接！请检查网络连接并刷新重试！若问题无法解决，请联系管理员");
        };
        websocket.onopen = function () {
            heartCheck.start();//开始心跳监测
            console.log("websocket connected!")
            websocket.onclose = function () {
                console.log("onclose");
                $.messager.confirm('提示', 'websocket连接被关闭，请检查网络或者是否从其他地方登陆。注意，点击确定后会重连', function (index) {
                    connectWebSocket();
                    layer.close(index);
                });
            };
            websocket.onerror = function () {
                console.log("onerror");
                $.messager.confirm('提示', 'websocket遇到错误，连接中断。点击确定后会重连', function (index) {
                    connectWebSocket();
                    layer.close(index);
                });

            };
            //接收到消息的回调方法
            websocket.onmessage = function (event) {
                //收到心跳消息重置心跳检测
                if (event.data == 'heartbeat') {
                    console.log("heartbeat");
                    heartCheck.reset();
                    return;
                }
                showMsg(event.data);
            }


        }

        return websocket;
    }
</script>
</html>

