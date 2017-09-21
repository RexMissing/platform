
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>质量分析管理平台-首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="lib/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
    <!--<script src="js/jquery-1.10.2.js" type="text/javascript"></script>-->
    <script src="js/jquery.json-2.4.min.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
    <script src="js/config.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function ()
        {
            $("#layout1").ligerLayout({topHeight:68,leftWidth:200});
            $("#titleContainer").load("title.html");
            $("#menuContainer").load("menu.html");
        });

    </script>
    <style type="text/css">
        body{ padding:0px; margin:0;overflow: hidden;}
        #layout1{  width:100%; margin:40px;  height:400px;
            margin:0; padding:0;}
        #accordion1 { height:270px;}
        h4{ margin:20px;}
        .l-dialog-tc {
            background: none repeat scroll 0 0 #E0EDFF;
        }
    </style>
</head>
<body>
<div id="layout1">
    <div id="menuContainer"  position="left"></div>
    <div id="mainContainer" position="center" title="" style="overflow-x: scroll; overflow-y: scroll;">

    </div>
    <div id="titleContainer" position="top"></div>
</div>

<div style="display:none;">

</div>
</body>
</html>
