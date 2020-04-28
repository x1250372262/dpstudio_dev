<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="net.ymate.platform.webmvc.context.WebContext" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.ymate.net/ymweb_core" prefix="ymp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>@{title}</title>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/admin/css/bootstrap3.3.7.min.css" >
    <link href="/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/admin/plugins/bootstrapvalidator/css/bootstrapValidator.min.css" rel="stylesheet">
    <link href="/admin/css/materialdesignicons.min.css" rel="stylesheet">
    <!--下拉多选插件-->
    <link href="/admin/plugins/bootstrap-select-1.13.7/dist/css/bootstrap-select.css" rel="stylesheet"/>
    <!--时间选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
    <!--上传文件-->
    <link rel="stylesheet" href="/admin/plugins/bootstrap-fileinput/bootstrap-fileinput.css">
    <link rel="stylesheet" href="/admin/plugins/css/plugins.css">
    <link href="/admin/css/style.min.css" rel="stylesheet">
    @{css}
</head>
<body>
@{body}

<div class="modal fade text-center picbig">
    <div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
        </div>
        <div class="modal-content">
            <img id="showImage" src="">
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg removeDio" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myLargeModalLabel">确认删除</h4>
            </div>
            <div class="modal-body">
                是否确定删除数据?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="deletes">删除</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg removeDios" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myLargeModalLabel">发货</h4>
            </div>
            <div class="modal-body">
                是否确定发货?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="toAudit">确认</button>
            </div>
        </div>
    </div>
</div>
<!--禁用启用-->
<div class="modal fade bs-example-modal-lg statusDio" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" data-backdrop="static">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myLargeModalLabel"></h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="statusbtn">确认</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var editors={}
    YMP = {
        baseUrl: "<ymp:url/>",
    }
</script>
<script type="text/javascript" src="/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="/admin/js/md5.js"></script>
<script type="text/javascript" src="/admin/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/admin/js/jquery-form.js"></script>
<script type="text/javascript" src="/admin/js/jquery.cokie.min.js"></script>
<script type="text/javascript" src="/admin/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="/admin/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="/admin/plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<!--消息提示-->
<script type="text/javascript" src="/admin/js/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/admin/js/lightyear.js"></script>

<script src="/admin/plugins/bootstrap-select-1.13.7/js/bootstrap-select.js"></script>
<script src="/admin/plugins/bootstrap-select-1.13.7/js/i18n/defaults-zh_CN.js"></script>

<!--时间选择插件-->
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/locale/zh-cn.js"></script>
<!--日期选择插件-->
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<!--标签插件-->
<script src="/admin/js/jquery-tags-input/jquery.tagsinput.min.js"></script>


<!--上传文件-->
<script src="/admin/plugins/bootstrap-fileinput/bootstrap-fileinput.js"></script>

<script src="/admin/js/upload.js"></script>
<script type="text/javascript" src="/admin/js/public/utils.js"></script>
<script type="text/javascript" src="/admin/js/form.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>

<script>
    $('body').attr('data-logobg', "color_8");
    $('body').attr('data-sidebarbg', "color_8");
    // // 设置主题配色
    // setTheme = function(input_name, data_name) {
    //     $("input[name='"+input_name+"']").click(function(){
    //         $('body').attr(data_name, $(this).val());
    //         $.cookie('the_'+input_name, $(this).val());
    //     });
    // }
    // setTheme('site_theme', 'data-theme');
    // setTheme('logo_bg', 'data-logobg');
    // setTheme('header_bg', 'data-headerbg');
    // setTheme('sidebar_bg', 'data-sidebarbg');
</script>
@{script}

</body>
</html>